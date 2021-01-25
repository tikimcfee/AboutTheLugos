package visual_interfaces.web.htmlComponents

import kotlinx.css.*
import visual_interfaces.web.htmlComponents.SimpleHTML.setStyles
import visual_interfaces.web.htmlComponents.componentClasses.mainPage.devImage
import visual_interfaces.web.htmlComponents.componentClasses.mainPage.mainBodyWrapper

object componentClasses {
    
    object mainPage {
        val mainBodyWrapper = "main-page-body-wrapper"
        val devImage = "main-page-dev-image"
    }
    
}

object NamedRules {
    const val clearfix = ".clearfix::after"
}

fun CSSBuilder.addClass(
    name: String,
    block: RuleSet
) = rule(".$name", block)

fun SimpleHTML.Html.setGlobalStyles() {
    setStyles {
        rule(NamedRules.clearfix) {
            content = QuotedString("")
            clear = Clear.both
            display = Display.table
        }
    
        // -- Media --
        rule("@media only screen and (max-width: 800px)") {
        
        }
    
        // Body
        addClass(mainBodyWrapper) {
            display = Display.flex
            flexWrap = FlexWrap.wrap
        }
        
        addClass(devImage) {
            borderRadius = 8.pt
            width = 128.pt
        }
    }
}
