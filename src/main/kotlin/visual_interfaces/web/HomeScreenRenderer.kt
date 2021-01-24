package visual_interfaces.web

import io.javalin.Context
import functionality.AppStateFunctions
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.componentClasses
import visual_interfaces.web.htmlComponents.setGlobalStyles

object HomeScreenRenderer {
    
    enum class FormParam(val id: String) {
        USER_EMAIL_TEXT_INPUT("userEmailTextInput")
    }
    
    fun AppStateFunctions.renderResponseTo(context: Context) {
        val rawHtml = with(SimpleHTML) {
            html {
                // Page setup (style, meta, etc.)
                setMetaData()
                setGlobalStyles()
                
                // Page content
                body {
                    div {
                        setCssClasses(componentClasses.mainPage.mainBodyWrapper)
                        
                    }
                }
            }
        }.toString()
        
        context.setTextResult(rawHtml)
    }
    
    fun AppStateFunctions.renderAboutPageTo(context: Context) {
        val rawHtml = with(SimpleHTML) {
            html {
                // Page setup (style, meta, etc.)
                setMetaData()
                setGlobalStyles()
                
                // Page content
                body {
                    div {
                        setCssClasses(componentClasses.mainPage.mainBodyWrapper)
                        
                    }
                }
            }
        }.toString()
        
        context.setTextResult(rawHtml)
    }
    
    private fun Context.setTextResult(text: String) {
        header("Content-Type", "text/html")
            .result(text)
    }
}
