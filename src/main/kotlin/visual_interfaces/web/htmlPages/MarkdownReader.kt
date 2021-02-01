package visual_interfaces.web.htmlPages

import org.intellij.markdown.flavours.commonmark.CommonMarkFlavourDescriptor
import org.intellij.markdown.html.HtmlGenerator
import org.intellij.markdown.parser.MarkdownParser

object MarkdownReader {
    private val flavour = CommonMarkFlavourDescriptor()
    private val parser = MarkdownParser(flavour)

    // A try at removing root 'body' tags
    fun createRawHtmlFromMarkdown(markdown: String): String {
        return parser.buildMarkdownTreeFromString(markdown).let { rootNode ->
            StringBuffer().apply {
                rootNode.children
                   .map { HtmlGenerator(markdown, it, flavour) }
                   .map { it.generateHtml() }
                   .forEach { append(it) }
            }.toString()
        }
    }
}