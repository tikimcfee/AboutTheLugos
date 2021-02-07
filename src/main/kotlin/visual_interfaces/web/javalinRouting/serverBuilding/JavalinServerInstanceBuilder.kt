package visual_interfaces.web.javalinRouting.serverBuilding

import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory
import org.eclipse.jetty.http2.HTTP2Cipher
import org.eclipse.jetty.http2.server.HTTP2ServerConnectionFactory
import org.eclipse.jetty.server.*
import org.eclipse.jetty.util.ssl.SslContextFactory
import visual_interfaces.web.javalinRouting.IPHelper
import java.nio.file.Paths

object JavalinServerInstanceBuilder {

    val server: Server by lazy {
        Server().apply {
            addHttpConnector()
            addHttp2Connector()
        }
    }

    private fun Server.addHttpConnector() {
        addConnector(
            ServerConnector(this).apply {
                port = IPHelper.preferredUnsafeHttpPort
                host = IPHelper.localNetworkIp
            }
        )
    }

    private fun Server.addHttp2Connector() {
        addConnector(
            ServerConnector(
                this,
                sslConnectionFactory,
                alpnConnectionFactory,
                http2ConnectionFactory,
                HttpConnectionFactory(httpsRequestCustomizerConfig)
            ).apply {
                port = IPHelper.preferredEncryptedHttpsPort
                host = IPHelper.localNetworkIp
            }
        )
    }

    // Protocol configurations
    private val httpConfig =
        HttpConfiguration().apply {
            sendServerVersion = false
            secureScheme = IPHelper.encryptedProtocolHttps
            securePort = IPHelper.preferredEncryptedHttpsPort
        }

    private val httpsRequestCustomizerConfig =
        HttpConfiguration(httpConfig).apply {
            addCustomizer(SecureRequestCustomizer())
        }

    // SSL Context
    private val sslContextFactory =
        SslContextFactory.Server().apply {
            setKeyStorePassword(readKeystorePassword())
            keyStorePath = keystoreFilePath
            provider = "Conscrypt" // Won't run on arm32 =( (Raspberry Pi 4)
            cipherComparator = HTTP2Cipher.COMPARATOR
        }

    // Connection factories
    private val http2ConnectionFactory =
        HTTP2ServerConnectionFactory(httpsRequestCustomizerConfig)

    private val alpnConnectionFactory =
        ALPNServerConnectionFactory().apply {
            defaultProtocol = "h2"
        }

    private val sslConnectionFactory =
        SslConnectionFactory(
            sslContextFactory,
            alpnConnectionFactory.protocol
        )

}

private val keystoreFilePath =
    System.getProperty("user.dir")
        .let { Paths.get(it) }
        .resolve("certs")
        .resolve("keystore.jks")
        .toAbsolutePath()
        .toString()

private fun readKeystorePassword() = try {
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