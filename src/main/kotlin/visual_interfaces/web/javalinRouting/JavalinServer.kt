package visual_interfaces.web.javalinRouting

import functionality.AppStateFunctions
import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import visual_interfaces.web.htmlPages.RouteRenderer.renderAboutPageTo
import visual_interfaces.web.htmlPages.RouteRenderer.renderHomePageTo

internal object ServerPaths {
    const val publicResourcePath = "/public"
}

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
            it.addStaticFiles(ServerPaths.publicResourcePath)
            if (enableDebugging) it.enableDevLogging()
        }
    }
    
    internal val runtimeState: AppStateFunctions by lazy {
        AppStateFunctions(
            // runtime app data class go here
        ).apply {
            // do stuff after construction, e.g. restoring state
        }
    }
    
    internal fun bindRoutes() {
        Route.values().forEach { route ->
            when (route) {
                Route.Root -> app.get(route.path) {
                    it.redirect(Route.Home.path)
                }
                Route.Home -> app.get(route.path) {
                    renderHomePageTo(it)
                }
                Route.About -> app.get(route.path) {
                    renderAboutPageTo(it)
                }
            }.run {
                // `run` enforces `when` compile time check for a known enum
                println("## Route [$route] loaded")
            }
        }
    }
}

fun JavalinServer.start() {
    app.start(IPHelper.preferredPort)
    bindRoutes()
}
