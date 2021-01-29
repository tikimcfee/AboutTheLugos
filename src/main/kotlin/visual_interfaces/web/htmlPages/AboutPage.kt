package visual_interfaces.web.htmlPages

import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.localImage
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.table
import visual_interfaces.web.htmlComponents.SimpleHTML.td
import visual_interfaces.web.htmlComponents.SimpleHTML.text
import visual_interfaces.web.htmlComponents.SimpleHTML.tr
import visual_interfaces.web.htmlComponents.componentClasses

fun SimpleHTML.Html.makeAboutPageContent() {
    setCssClasses(componentClasses.mainPage.mainBodyWrapper)
    table {
        tr {
            td {
                text("Oh hey, it's me!")
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