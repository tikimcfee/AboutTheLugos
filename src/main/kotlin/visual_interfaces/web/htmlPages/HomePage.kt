package visual_interfaces.web.htmlPages

import visual_interfaces.web.htmlComponents.ComponentClasses
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.h2
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.text

fun SimpleHTML.Html.makeHomePageContent() {
    setCssClasses(ComponentClasses.MainPage.mainBodyWrapper)

    h2 { text("Interesting stuff will go here!") }
}