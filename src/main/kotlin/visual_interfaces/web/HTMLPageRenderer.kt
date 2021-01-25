package visual_interfaces.web

import io.javalin.http.Context
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.componentClasses
import visual_interfaces.web.htmlComponents.setGlobalStyles

object HTMLPageRenderer {
    
    enum class FormParam(val id: String) {
        USER_EMAIL_TEXT_INPUT("userEmailTextInput")
    }
    
    fun renderHomePageTo(context: Context) {
        val rawHtml = with(SimpleHTML) {
            html {
                // Page setup (style, meta, etc.)
                setMetaData()
                setGlobalStyles()
                
                // Page content
                body {
                    div {
                        setCssClasses(componentClasses.mainPage.mainBodyWrapper)
                        
                        table {
                            tr {
                                td {
                                    text("Hello, world! Thanks for visiting. I'll be bringing this up sooner or later. Maybe.")
                                }
                            }
                            tr {
                                td {
                                    localImage(
                                        "WhatAMug.jpg",
                                        "A goofy image of the developer offering a fist bump while hunching with duckface."
                                    ) {
                                        setCssClasses(componentClasses.mainPage.devImage)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.toString()
        
        context.setTextResult(rawHtml)
    }
    
    fun renderAboutPageTo(context: Context) {
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
