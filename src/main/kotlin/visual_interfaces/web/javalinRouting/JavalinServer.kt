package visual_interfaces.web.javalinRouting

import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import visual_interfaces.web.htmlPages.RouteRenderer
import visual_interfaces.web.htmlPages.liveArticles.LiveArticleLoader

class JavalinServer {
    
    private val enableDebugging = false
    
    private val server: Server by lazy {
        Server().apply {
            val defaultConnector = ServerConnector(this).apply {
                host = IPHelper.localNetworkIp
                port = IPHelper.preferredPort
            }
            connectors = arrayOf(defaultConnector)
        }
    }
    
    internal val app: Javalin by lazy {
        Javalin.create {
            it.server { server }
            it.addStaticFiles(CommonBaseUrls.publicResourcePath)
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
}

fun JavalinServer.start() {
    LiveArticleLoader.beginArticleObservation()
    app.start(IPHelper.preferredPort)
    bindRoutes()
}
