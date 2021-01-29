package visual_interfaces.web.htmlComponents


import kotlinx.css.*
import kotlinx.css.Display.block
import kotlinx.css.Overflow.hidden
import kotlinx.css.Position.fixed
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecoration.Companion.none
import visual_interfaces.web.htmlComponents.SimpleHTML.setStyles
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticSideBar
import visual_interfaces.web.htmlComponents.componentClasses.mainPage.devImage
import visual_interfaces.web.htmlComponents.componentClasses.mainPage.mainBodyWrapper
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticSideBarAnchor
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticSideBarAnchorHover

object componentClasses {
    object shared {
        val staticSideBar = "static-side-bar"
        val staticSideBarAnchor = "static-side-bar a"
        val staticSideBarAnchorHover = "static-side-bar a:hover"
    }

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
//            display = Display.table
        }
    
        // -- Media --
        rule("@media only screen and (max-width: 800px)") {
        
        }

        // Sidebar
        val sidebarWidth = 160.px

        addClass(staticSideBar) {
            height = 100.pct
            width = sidebarWidth
            position = fixed
            zIndex = 1
            top = 0.px
            left = 0.px
            backgroundColor = Color.black
            overflowX = hidden
            paddingTop = 20.px
        }

        addClass(staticSideBarAnchor) {
            padding = "6px 8px 6px 16px" // t, r, b, l
            textDecoration = none
            fontSize = 25.px
            color = Color.gray
            display = block
        }

        addClass(staticSideBarAnchorHover) {
            color = Color.lightGray
        }

        // Body
        addClass(mainBodyWrapper) {
            marginLeft = sidebarWidth
            padding = "0px 10px"
        }
        
        addClass(devImage) {
            borderRadius = 8.pt
            width = 128.pt
        }
    }
}