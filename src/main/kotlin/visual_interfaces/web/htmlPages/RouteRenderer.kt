package visual_interfaces.web.htmlPages

import io.javalin.http.Context
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.Html
import visual_interfaces.web.htmlComponents.setGlobalStyles
import visual_interfaces.web.javalinRouting.Route

object RouteRenderer {

    internal val navigationRoutes by lazy { arrayOf(
       Route.Home,
       Route.About
    ) }

    fun renderHomePageTo(context: Context) {
        context.renderHtml(
           inSharedPageFrame(Route.Home) {
               makeHomePageContent()
           }
        )
    }
    
    fun renderAboutPageTo(context: Context) {
        context.renderHtml(
           inSharedPageFrame(Route.About) {
               makeAboutPageContent()
           }
        )
    }
}

private fun Context.renderHtml(html: Html) =
   setTextResult(html.toString())

private fun inSharedPageFrame(
    currentRoute: Route,
    builder: Html.() -> Unit
) = with(SimpleHTML) {
    html {
        setMetaData()
        setGlobalStyles()

        body {
            div {
                setCssClasses(Shared.staticNavigationBar)
                RouteRenderer.navigationRoutes.forEach { route ->
                    link(route.path, route.displayName) {
                        setCssClasses(route.anchorSelectionClass(currentRoute))
                    }
                }
            }

            div { builder() }
        }
    }
}

private val Route.displayName: String
    get() = when (this) {
        Route.Root -> "Magic"
        Route.Home -> "Home"
        Route.About -> "About"
    }

private fun Route.anchorSelectionClass(
    currentRoute: Route
) = when (currentRoute) {
    this -> Shared.staticNavigationBarAnchorCurrent
    else -> Shared.staticNavigationBarAnchorOther
}

private fun Context.setTextResult(text: String) =
   header("Content-Type",  "text/html").result(text)