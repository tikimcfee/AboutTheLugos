package visual_interfaces.web.javalinRouting

import functionality.AppStateFunctions
import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import visual_interfaces.web.HomeScreenRenderer.renderResponseTo

class JavalinServer {
    
    private val enableDebugging = false
    private val defaultPort = 7000
    
    private val server: Server by lazy {
        Server().apply {
            connectors = arrayOf(ServerConnector(this).apply {
                host = Route.host
                port = Route.port.toInt()
            })
        }
    }
    
    private val app: Javalin by lazy {
        Javalin.create()
            .apply { if (enableDebugging) enableDebugLogging() }
            .server { server }
    }
    
    private val runtimeState: AppStateFunctions by lazy {
        AppStateFunctions(
            // runtime app data class go here
        ).apply {
            // do stuff after construction, e.g. restoring state
        }
    }
    
    fun start() {
        app.start(defaultPort)
        bindRoutes()
    }
    
    private fun bindRoutes() {
        Route.startupRouteSet.forEach { route ->
            when (route) {
                Route.Home -> app.get(route.name) {
                    runtimeState.renderResponseTo(it)
                }
                Route.About -> app.get(route.name) {
                    runtimeState.renderResponseTo(it)
                }
            }.run {
                // `run` enforces `when` compile time check for a known enum
                println("## Route [$route] loaded")
            }
        }
    }
}
