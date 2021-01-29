package visual_interfaces.web.htmlPages

import io.javalin.http.Context
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.Html
import visual_interfaces.web.htmlComponents.componentClasses.shared
import visual_interfaces.web.htmlComponents.setGlobalStyles

object HTMLPageRenderer {

    fun renderHomePageTo(context: Context) {
        val rawHtml = inSharedPageFrame {
            makeHomePageContent()
        }.toString()
        
        context.setTextResult(rawHtml)
    }
    
    fun renderAboutPageTo(context: Context) {
        val rawHtml = inSharedPageFrame {
            makeAboutPageContent()
        }.toString()
        
        context.setTextResult(rawHtml)
    }
    
    private fun Context.setTextResult(text: String) {
        header("Content-Type", "text/html")
            .result(text)
    }

    private fun inSharedPageFrame(builder: Html.() -> Unit): Html {
        return with(SimpleHTML) {
            html {
                setMetaData()
                setGlobalStyles()

                body {
                    div {
                        setCssClasses(shared.staticSideBar)
                        link("home", "Words")
                        link("about", "About")
//                        link("devhistory.html", "Devin'")
//                        link("coolstuff.html", "Cool Stuff")
                    }

                    div {
                        builder()
                    }
                }
            }
        }
    }
}
