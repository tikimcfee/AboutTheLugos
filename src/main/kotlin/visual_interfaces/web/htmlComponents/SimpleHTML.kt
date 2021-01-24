package visual_interfaces.web.htmlComponents

import visual_interfaces.web.HomeScreenRenderer
import kotlinx.css.CSSBuilder
import visual_interfaces.web.javalinRouting.Route

@Suppress("MemberVisibilityCanBePrivate", "unused")
object SimpleHTML {
    
    fun <T : Tag> T.setAttribute(name: String, value: String?): T = apply {
        if (value != null) {
            attributes.add(Attribute(name, value))
        }
    }
    
    fun <T : Tag> T.setCssClasses(vararg classes: String): T = apply {
        classes.joinToString(" ").let {
            attributes.add(Attribute("class", it))
        }
    }
    
    fun <T : Tag> Tag.initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }
    
    class Html : Tag("html")
    class Head : Tag("head")
    class Style : Tag("style")
    class Meta : Tag("meta")
    class Body : Tag("body")
    
    class Table : Tag("table")
    class TR : Tag("tr")
    class TD : Tag("td")
    class Text(val text: String) : Tag("b") {
        override fun toString() = text
    }
    
    class Center : Tag("center")
    class Span : Tag("span")
    class Div : Tag("div")
    class LineBreak : Tag("br")
    
    class Form : Tag("form")
    class Label : Tag("label")
    class Input : Tag("input")
    class Button : Tag("button")
    
    class Select : Tag("select")
    class Option(value: String) : Tag("option") {
        init {
            setAttribute("value", value)
        }
    }
    
    // ------------------------------------
    // HTML
    // ------------------------------------
    fun html(init: Html.() -> Unit): Html =
        Html().apply(init)
    
    fun Html.head(init: Head.() -> Unit) =
        initTag(Head(), init)
    
    fun Html.style(init: Style.() -> Unit) =
        initTag(Style(), init)
    
    fun Html.meta(init: Meta.() -> Unit) =
        initTag(Meta(), init)
    
    fun Html.body(init: Body.() -> Unit) =
        initTag(Body(), init)
    
    fun Html.center(init: Center.() -> Unit) =
        initTag(Center(), init)
    
    
    // ------------------------------------
    // Tables
    // ------------------------------------
    fun Table.tr(color: String? = null, init: TR.() -> Unit) =
        initTag(TR(), init)
            .setAttribute("bgcolor", color)
    
    fun TR.td(color: String? = null, align: String = "right", init: TD.() -> Unit) =
        initTag(TD(), init)
            .setAttribute("align", align)
            .setAttribute("bgcolor", color)
    
    
    // ------------------------------------
    // Forms
    // ------------------------------------
    fun Tag.inputTag(
        labelText: String,
        forAttr: HomeScreenRenderer.FormParam,
        input: Input.() -> Unit = {}
    ) = initTag(Input()) {
        setAttribute("name", forAttr.id)
        setAttribute("id", forAttr.id)
        setAttribute("placeholder", labelText)
        input(this)
    }
    
    fun Tag.selection(init: Select.() -> Unit) =
        initTag(Select(), init)
    
    fun Tag.option(value: String, init: Option.() -> Unit) =
        initTag(Option(value), init)
    
    fun Tag.selectionDropdown(
        forAttr: HomeScreenRenderer.FormParam,
        initSelection: Select.() -> Unit,
        initOption: Option.() -> Unit,
        vararg selections: String
    ) {
        selection {
            initSelection()
            
            setAttribute("for", forAttr.id)
            setAttribute("name", forAttr.id)
            
            selections.forEach {
                option(it) {
                    initOption()
                    text(it)
                }
            }
        }
    }
    
    fun Tag.newCheckbox(
        labelText: String,
        forAttr: HomeScreenRenderer.FormParam,
        input: ((Input) -> Unit) = {}
    ) {
        initTag(Input()) {
            setAttribute("type", "checkbox")
            setAttribute("name", forAttr.id)
            setAttribute("id", forAttr.id)
            setAttribute("value", forAttr.id)
            text(labelText)
            input(this)
        }
    }
    
    fun Form.hiddenInput(
        forAttr: HomeScreenRenderer.FormParam,
        input: ((Input) -> Unit) = {}
    ) {
        //<input type = "hidden" name = "topic" value = "something" />
        initTag(Input()) {
            setAttribute("style", "visibility: hidden;")
            setAttribute("name", forAttr.id)
            input(this)
        }
    }
    
    fun Form.addActionAndMethod(route: Route) {
        with(SimpleHTML) {
            this@addActionAndMethod.setAttribute("action", route.path)
            this@addActionAndMethod.setAttribute("method", route.method)
        }
    }
    
    // ------------------------------------
    // Simple Children
    // ------------------------------------
    fun Tag.table(init: Table.() -> Unit) =
        initTag(Table(), init)
    
    fun Tag.form(init: Form.() -> Unit) =
        initTag(Form(), init)
    
    fun Tag.span(init: Span.() -> Unit) =
        initTag(Span(), init)
    
    fun Tag.text(s: Any?) =
        initTag(Text(s.toString()), {})
    
    fun Tag.lineBreak() =
        initTag(LineBreak(), {})
    
    fun Tag.button(init: Button.() -> Unit) =
        initTag(Button(), init)
    
    fun Tag.div(init: Div.() -> Unit) =
        initTag(Div(), init)
    
    // ------------------------------------
    // Buttons
    // ------------------------------------
    fun Tag.hiddenInputButton(
        text: String,
        buttonInput: String,
        formParam: HomeScreenRenderer.FormParam
    ) = button {
        text(text)
        setAttribute("value", buttonInput)
        setAttribute("name", formParam.id)
        setAttribute("form", formParam.name)
    }
    
    // ------------------------------------
    // CSS
    // ------------------------------------
    fun Html.setStyles(receiver: CSSBuilder.() -> Unit) {
        head {
            style {
                text(
                    CSSBuilder().apply(receiver).toString()
                )
            }
        }
    }
    
    fun Html.setMetaData() {
        meta {
            // <meta name="viewport" content="width=device-width, initial-scale=1.0">
            setAttribute("name", "viewport")
            setAttribute("content", "width=device-width, initial-scale=1.0")
            setAttribute("charset", "UTF-8")
        }
    }
}
