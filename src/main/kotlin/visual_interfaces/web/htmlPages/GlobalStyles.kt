package visual_interfaces.web.htmlPages

import kotlinx.css.CSSBuilder
import kotlinx.css.Clear
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.LinearDimension.Companion.auto
import kotlinx.css.Overflow
import kotlinx.css.Position
import kotlinx.css.QuotedString
import kotlinx.css.backgroundColor
import kotlinx.css.clear
import kotlinx.css.color
import kotlinx.css.content
import kotlinx.css.display
import kotlinx.css.fontFamily
import kotlinx.css.fontSize
import kotlinx.css.height
import kotlinx.css.left
import kotlinx.css.marginLeft
import kotlinx.css.marginRight
import kotlinx.css.marginTop
import kotlinx.css.overflowX
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import kotlinx.css.px
import kotlinx.css.textDecoration
import kotlinx.css.top
import kotlinx.css.width
import kotlinx.css.zIndex
import visual_interfaces.web.htmlComponents.ComponentClasses.MainPage.mainBodyWrapper
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared.staticNavigationBar
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared.staticNavigationBarAnchor
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared.staticNavigationBarAnchorCurrent
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared.staticNavigationBarAnchorOther
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared.staticSideBarAnchorHover
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared.topContentContainer
import visual_interfaces.web.htmlComponents.NamedRules
import visual_interfaces.web.htmlComponents.addClass

fun CSSBuilder.globalStyleBuilder() {
    rule(NamedRules.clearfix) {
        content = QuotedString("")
        clear = Clear.both
    }

    // Sidebar
    val sidebarHeight = 56.px

    addClass(topContentContainer) {
        backgroundColor = ColorPalette.defaultPageBackgroundColor
        color = ColorPalette.defaultTextColor
        fontFamily = "Arial, sans serif"
    }

    addClass(staticNavigationBar) {
        height = sidebarHeight
        width = 100.pct
        position = Position.fixed
        zIndex = 1
        top = 0.px
        left = 0.px
        backgroundColor = Color.black
        overflowX = Overflow.hidden
    }

    addClass(staticNavigationBarAnchor) {
        padding = "16px 8px 16px 16px" // t, r, b, l
        fontSize = 20.px
        color = Color.gray
        display = Display.inlineBlock
    }

    addClass(staticNavigationBarAnchorCurrent) {
        textDecoration(TextDecorationLine.underline)
        put("text-underline-offset", "4px") // no built-in support for this yet
    }

    addClass(staticNavigationBarAnchorOther) {
        textDecoration = TextDecoration.none
    }

    addClass(staticSideBarAnchorHover) {
        color = Color.lightGray
    }

    // Body
    addClass(mainBodyWrapper) {
        marginTop = sidebarHeight + 8.px
        marginLeft = auto
        marginRight = auto
        width = 67.pct
    }

    // -- Media --
    rule("@media only screen and (max-width: 800px)") {
        addClass(mainBodyWrapper) {
            width = 95.pct
        }
    }
}

object ColorPalette {
    val defaultTextColor =
       Color("#cccccc")
    val defaultPageBackgroundColor =
       Color("#121212")

    val articleListLinkContainerBorder =
       Color("#444444")
    val articleListLinkColor =
       Color("#6071a0")

}