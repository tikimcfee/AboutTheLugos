package visual_interfaces.web.htmlPages

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import kotlinx.css.properties.TextDecorationLine
import kotlinx.css.properties.textDecoration
import visual_interfaces.web.htmlComponents.ComponentClasses
import visual_interfaces.web.htmlComponents.NamedRules
import visual_interfaces.web.htmlComponents.addClass

fun CSSBuilder.globalStyleBuilder() {
    rule(NamedRules.clearfix) {
        content = QuotedString("")
        clear = Clear.both
    }

    // -- Media --
    rule("@media only screen and (max-width: 800px)") {

    }

    // Sidebar
    val sidebarHeight = 56.px

    addClass(ComponentClasses.Shared.staticNavigationBar) {
        height = sidebarHeight
        width = 100.pct
        position = Position.fixed
        zIndex = 1
        top = 0.px
        left = 0.px
        backgroundColor = Color.black
        overflowX = Overflow.hidden
    }

    addClass(ComponentClasses.Shared.staticNavigationBarAnchor) {
        padding = "16px 8px 16px 16px" // t, r, b, l
        fontSize = 20.px
        color = Color.gray
        display = Display.inlineBlock
    }

    addClass(ComponentClasses.Shared.staticNavigationBarAnchorCurrent) {
        textDecoration(TextDecorationLine.underline)
        put("text-underline-offset", "4px") // no built-in support for this yet
    }

    addClass(ComponentClasses.Shared.staticNavigationBarAnchorOther) {
        textDecoration = TextDecoration.none
    }

    addClass(ComponentClasses.Shared.staticSideBarAnchorHover) {
        color = Color.lightGray
    }

    // Body
    addClass(ComponentClasses.MainPage.mainBodyWrapper) {
        marginTop = sidebarHeight
    }
}
