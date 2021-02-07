package visual_interfaces.web.javalinRouting.serverBuilding

import io.javalin.Javalin
import io.javalin.http.staticfiles.Location
import visual_interfaces.web.htmlPages.RouteRenderer
import visual_interfaces.web.htmlPages.staticPages.liveArticles.LiveArticleLoader
import visual_interfaces.web.javalinRouting.CommonBaseUrls
import visual_interfaces.web.javalinRouting.Route


class JavalinServerBootstrapper {
    
    private val enableDebugging = false
    
    internal val app: Javalin by lazy {
        Javalin.create {
            it.server { JavalinServerInstanceBuilder.server }
            it.addStaticFiles(CommonBaseUrls.publicResourcePath, Location.EXTERNAL)
            if (enableDebugging) it.enableDevLogging()
        }.apply {
            before {
                println("Request :: ${it.protocol()} :: ${it.path()} :: ${it.url()} }")
                when (it.protocol()) {
                    "HTTP/1.1" -> {
                        val newUrl = it.url().replaceFirst("http", "https")
                        it.redirect(newUrl, 301) // 301 == "Moved Permanently"
                    }
                }
            }
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

fun JavalinServerBootstrapper.applicationStarted() {
    LiveArticleLoader.beginArticleObservation()
    bindRoutes()
    app.start()
}