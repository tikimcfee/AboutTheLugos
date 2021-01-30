package visual_interfaces.web.htmlPages

import io.javalin.http.Context
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.Html
import visual_interfaces.web.htmlComponents.componentClasses.shared
import visual_interfaces.web.htmlComponents.setGlobalStyles
import visual_interfaces.web.javalinRouting.Route

object HTMLPageRenderer {

    enum class FormParam(val id: String) {
        USER_EMAIL_TEXT_INPUT("userEmailTextInput")
    }

    fun renderHomePageTo(context: Context) {
        val rawHtml = inSharedPageFrame(Route.Home) {
            makeHomePageContent()
        }.toString()
        
        context.setTextResult(rawHtml)
    }
    
    fun renderAboutPageTo(context: Context) {
        val rawHtml = inSharedPageFrame(Route.About) {
            makeAboutPageContent()
        }.toString()
        
        context.setTextResult(rawHtml)
    }
    
    private fun Context.setTextResult(text: String) {
        header("Content-Type", "text/html")
            .result(text)
    }

    private val navigationRoutes: Array<Route> by lazy {
        arrayOf(
           Route.Home,
           Route.About
        )
    }

    private val Route.displayName: String
        get() { return when (this) {
            Route.Root -> "Magic"
            Route.Home -> "Home"
            Route.About -> "About"
        }}

    private fun Route.anchorSelectionClass(currentRoute: Route): String {
        return when (currentRoute) {
            this -> shared.staticNavigationBarAnchorCurrent
            else -> shared.staticNavigationBarAnchorOther
        }
    }

    private fun inSharedPageFrame(
        currentRoute: Route,
        builder: Html.() -> Unit
    ): Html {
        return with(SimpleHTML) {
            html {
                setMetaData()
                setGlobalStyles()

                body {
                    div {
                        setCssClasses(shared.staticNavigationBar)
                        navigationRoutes.forEach {
                            link(it.path, it.displayName).apply {
                                setCssClasses(it.anchorSelectionClass(currentRoute))
                            }
                        }
                    }

                    div {
                        builder()
                    }
                }
            }
        }
    }
}
