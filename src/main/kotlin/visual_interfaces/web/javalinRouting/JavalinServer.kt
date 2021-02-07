package visual_interfaces.web.javalinRouting

import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory
import org.eclipse.jetty.http2.HTTP2Cipher
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory
import org.eclipse.jetty.server.*
import org.eclipse.jetty.util.ssl.SslContextFactory
import visual_interfaces.web.htmlPages.RouteRenderer
import visual_interfaces.web.htmlPages.staticPages.liveArticles.LiveArticleLoader
import visual_interfaces.web.javalinRouting.JavalinServerFilesystemTools.readKeystorePassword
import java.nio.file.Paths


class JavalinServer {
    
    private val enableDebugging = false
    
    private val server: Server by lazy {
        Server().apply {
            val httpConfig = HttpConfiguration().apply {
                sendServerVersion = false
                secureScheme = IPHelper.encryptedProtocolHttps
                securePort = IPHelper.preferredEncryptedHttpsPort
            }
            val httpsConfig = HttpConfiguration(httpConfig).apply {
                addCustomizer(SecureRequestCustomizer())
            }
            val sslContextFactory = SslContextFactory.Server().apply {
                setKeyStorePassword(readKeystorePassword())
                keyStorePath = JavalinServerFilesystemTools.keystoreFilePath
                provider = "Conscrypt"
                cipherComparator = HTTP2Cipher.COMPARATOR
            }

            // Connection Factories
            val http2ConnectionFactory = HTTP2ServerConnectionFactory(httpsConfig)
            val alpnConnectionFactory = ALPNServerConnectionFactory().apply {
                defaultProtocol = "h2"
            }
            val sslConnectionFactory = SslConnectionFactory(
                sslContextFactory,
                alpnConnectionFactory.protocol
            )

            // HTTP/2 Connector
            val http2Connector = ServerConnector(this,
                sslConnectionFactory,
                alpnConnectionFactory,
                http2ConnectionFactory,
                HttpConnectionFactory(httpsConfig)
            ).apply {
                port = IPHelper.preferredEncryptedHttpsPort
                host = IPHelper.localNetworkIp
            }

            addConnector(http2Connector)
        }
    }
    
    internal val app: Javalin by lazy {
        Javalin.create {
            it.server { server }
            it.addStaticFiles(CommonBaseUrls.publicResourcePath, Location.EXTERNAL)
            if (enableDebugging) it.enableDevLogging()
        }
    }
    
    internal fun bindRoutes() {
        Route.values().forEach { route ->
            when (route) {
                Route.Root -> app.get(route.path) {
                    it.redirect(Route.Home.path)
                }
                else -> app.get(route.path) {
                    with(RouteRenderer) {
                        it.renderPageAt(route)
                    }
                }
            }.run {
                // `run` enforces `when` compile time check for a known enum
                println("## Route [$route] loaded")
            }
        }
    }

//    Use if unable to get a standalone certbot server working.
//    internal fun bindAcmeChallengeRoute() {
//        app.get(acmeWellKnownPath) {
//            val challengeId = it.pathParam(acmeWellKnownPathId)
//            it.result("$challengeId.$acmePersonalId")
//        }
//    }
}

fun JavalinServer.start() {
    LiveArticleLoader.beginArticleObservation()
    app.start(IPHelper.preferredUnsafeHttpPort)
    bindRoutes()
}

private object JavalinServerFilesystemTools {
    val keystoreFilePath =
       System.getProperty("user.dir")
          .let { Paths.get(it) }
          .resolve("certs")
          .resolve("keystore.jks")
          .toAbsolutePath()
          .toString()

    fun readKeystorePassword() = try {
        System.getProperty("user.dir")
            .let { Paths.get(it) }
            .resolve("certs")
            .resolve("kp")
            .toFile()
            .readText()
            .trim()
        } catch (exception: Exception) {
            println(exception)
            ""
        }
}