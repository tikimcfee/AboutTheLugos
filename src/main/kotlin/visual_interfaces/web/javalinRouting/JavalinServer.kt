package visual_interfaces.web.javalinRouting

import functionality.AppStateFunctions
import io.javalin.Javalin
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import visual_interfaces.web.HTMLPageRenderer.renderAboutPageTo
import visual_interfaces.web.HTMLPageRenderer.renderHomePageTo

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
        Javalin.create()
            .apply {
                if (enableDebugging) enableDebugLogging()
                enableStaticFiles(ServerPaths.publicResourcePath)
            }
            .server { server }
    }
    
    internal val runtimeState: AppStateFunctions by lazy {
        AppStateFunctions(
            // runtime app data class go here
        ).apply {
            // do stuff after construction, e.g. restoring state
        }
    }
    
    internal fun bindRoutes() {
        Route.startupRouteSet.forEach { route ->
            when (route) {
                Route.Home -> app.get(route.name) {
                    renderHomePageTo(it)
                }
                Route.About -> app.get(route.name) {
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
