package visual_interfaces.web.htmlComponents

import kotlinx.css.*
import visual_interfaces.web.htmlComponents.SimpleHTML.setStyles
import visual_interfaces.web.htmlComponents.componentClasses.mainPage.mainBodyWrapper
import visual_interfaces.web.htmlComponents.componentClasses.transactionInput.container.vertical


object componentClasses {
    
    object mainPage {
        val mainBodyWrapper = "main-page-body-wrapper"
    }
    
    object transactionInput {
        object container {
            val vertical = "transaction-input-container-vertical"
            val horizontal = "transaction-input-container-vertical"
        }
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
            addClass(vertical) {
                declarations["order"] = -1
                width = 100.pct
            }
        }
    
        // -----------------------------
        // Duh bawdee
        // -----------------------------
        addClass(mainBodyWrapper) {
            display = Display.flex
            flexWrap = FlexWrap.wrap
        }
    }
}
