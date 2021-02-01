package visual_interfaces.web.htmlComponents


import kotlinx.css.CSSBuilder
import kotlinx.css.RuleSet

object ComponentClasses {
    object Shared {
        const val staticNavigationBar = "static-side-bar"
        const val staticNavigationBarAnchor = "static-side-bar a"
        const val staticNavigationBarAnchorCurrent = "static-side-bar-current"
        const val staticNavigationBarAnchorOther = "static-side-bar-other"
        const val staticSideBarAnchorHover = "static-side-bar a:hover"
    }

    object MainPage {
        const val mainBodyWrapper = "main-page-body-wrapper"
    }

    object AboutPage {
        const val devImageWrapper = "about-page-image-wrapper"
        const val devImage = "main-page-dev-image"
    }
}

object NamedRules {
    @Suppress("SpellCheckingInspection")
    const val clearfix = ".clearfix::after"
}

fun CSSBuilder.addClass(
    name: String,
    block: RuleSet
) = rule(".$name", block)