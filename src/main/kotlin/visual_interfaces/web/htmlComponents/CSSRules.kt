package visual_interfaces.web.htmlComponents


import kotlinx.css.*
import kotlinx.css.Display.block
import kotlinx.css.Display.inlineBlock
import kotlinx.css.Overflow.hidden
import kotlinx.css.Position.fixed
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecoration.Companion.none
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import visual_interfaces.web.htmlComponents.SimpleHTML.setAttribute
import visual_interfaces.web.htmlComponents.SimpleHTML.setStyles
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticNavigationBar
import visual_interfaces.web.htmlComponents.componentClasses.mainPage.devImage
import visual_interfaces.web.htmlComponents.componentClasses.mainPage.mainBodyWrapper
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticNavigationBarAnchor
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticNavigationBarAnchorCurrent
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticNavigationBarAnchorOther
import visual_interfaces.web.htmlComponents.componentClasses.shared.staticSideBarAnchorHover

object componentClasses {
    object shared {
        val staticNavigationBar = "static-side-bar"
        val staticNavigationBarAnchor = "static-side-bar a"
        val staticNavigationBarAnchorCurrent = "static-side-bar-current"
        val staticNavigationBarAnchorOther = "static-side-bar-other"
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
        val sidebarHeight = 64.px

        addClass(staticNavigationBar) {
            height = sidebarHeight
            width = 100.pct
            position = fixed
            zIndex = 1
            top = 0.px
            left = 0.px
            backgroundColor = Color.black
            overflowX = hidden
//            paddingTop = 20.px
        }

        addClass(staticNavigationBarAnchor) {
            padding = "16px 8px 16px 16px" // t, r, b, l
            fontSize = 25.px
            color = Color.gray
            display = inlineBlock
        }

        addClass(staticNavigationBarAnchorCurrent) {
            textDecoration(TextDecorationLine.underline)
            put("text-underline-offset", "4px") // no built-in support for this yet
        }

        addClass(staticNavigationBarAnchorOther) {
            textDecoration = none
        }

        addClass(staticSideBarAnchorHover) {
            color = Color.lightGray
        }

        // Body
        addClass(mainBodyWrapper) {
            marginTop = sidebarHeight
            padding = "0px 10px"
        }
        
        addClass(devImage) {
            borderRadius = 8.pt
            width = 128.pt
        }
    }
}