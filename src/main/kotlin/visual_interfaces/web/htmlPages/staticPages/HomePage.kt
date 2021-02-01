package visual_interfaces.web.htmlPages.staticPages

import visual_interfaces.web.htmlComponents.ComponentClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.h2
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.text
import visual_interfaces.web.htmlComponents.Tag

fun Tag.makeHomePageContent() {
    setCssClasses(ComponentClasses.MainPage.mainBodyWrapper)

    h2 { text("Interesting stuff will go here!") }
}