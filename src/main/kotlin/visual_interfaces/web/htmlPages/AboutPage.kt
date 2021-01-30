package visual_interfaces.web.htmlPages

import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.div
import visual_interfaces.web.htmlComponents.SimpleHTML.localImage
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.text
import visual_interfaces.web.htmlComponents.ComponentClasses.AboutPage.devImage
import visual_interfaces.web.htmlComponents.ComponentClasses.MainPage.mainBodyWrapper

fun SimpleHTML.Html.makeAboutPageContent() {
    setCssClasses(mainBodyWrapper)
//    div {
//        text("Oh hey, it's me!")
//    }


    div {
        localImage(
           "WhatAMug.jpg",
           "A goofy image of the developer offering a fist bump while hunching with duckface."
        ) {
            setCssClasses(devImage)
        }
        text("'Ivan' from the set of Lugo's")
    }
}