package visual_interfaces.web.htmlPages.staticPages.liveArticles

import kotlinx.css.BorderStyle.solid
import kotlinx.css.CSSBuilder
import kotlinx.css.Color.Companion.gray
import kotlinx.css.Color.Companion.white
import kotlinx.css.FontStyle.Companion.italic
import kotlinx.css.FontWeight
import kotlinx.css.borderColor
import kotlinx.css.borderRadius
import kotlinx.css.borderStyle
import kotlinx.css.borderWidth
import kotlinx.css.color
import kotlinx.css.fontSize
import kotlinx.css.fontStyle
import kotlinx.css.fontWeight
import kotlinx.css.margin
import kotlinx.css.opacity
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.TextDecoration.Companion.none
import kotlinx.css.px
import kotlinx.css.textDecoration
import visual_interfaces.web.htmlComponents.ComponentClasses
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.link
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkContainer
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkHover
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkSummary
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkVisited
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkVisitedHover
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.div
import visual_interfaces.web.htmlComponents.SimpleHTML.h3
import visual_interfaces.web.htmlComponents.SimpleHTML.h4
import visual_interfaces.web.htmlComponents.SimpleHTML.h6
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.spannedLink
import visual_interfaces.web.htmlComponents.SimpleHTML.text
import visual_interfaces.web.htmlComponents.Tag
import visual_interfaces.web.htmlComponents.addClass
import visual_interfaces.web.htmlPages.ColorPalette.articleListLinkColor
import visual_interfaces.web.htmlPages.ColorPalette.articleListLinkContainerBorder
import visual_interfaces.web.javalinRouting.Route.ArticleList

fun Tag.makeArticleListContent() {
    setCssClasses(ComponentClasses.MainPage.mainBodyWrapper)

    h3 { text("Index of Readable Things") }
    h4 { text("I wrote this stuff for some reason. Feel free to consume the media.") }

    with(LiveArticleLoader) {
        articleList.forEach { article ->
            div {
                spannedLink(article.linkHref, article.linkDisplay,
                   {
                       setCssClasses(linkContainer)
                       h6 {
                           setCssClasses(linkSummary)
                           text(article.linkSummary)
                       }
                   },
                   {
                       setCssClasses(link, linkHover, linkVisited)
                   }
                )
            }
        }
    }
}

val articleListPageStyles: CSSBuilder.() -> Unit = {
    addClass(link) {
        color = articleListLinkColor
        fontWeight = FontWeight.w500
        textDecoration = none
    }
    addClass(linkHover) {
        color = white
    }
    addClass(linkVisited) {
        color = gray
    }
    addClass(linkVisitedHover) {
        color = white
    }
    addClass(linkContainer) {
        margin = "4px"
        padding = "8px 8px 8px 4px"
        borderStyle = solid
        borderWidth = 1.px
        borderRadius = 4.px
        borderColor = articleListLinkContainerBorder
    }
    addClass(linkSummary) {
        margin = "8px 0px 8px 16px"
        opacity = 67
        fontStyle = italic
    }
}

private val ArticleFile.linkSummary
    get() = meta.summary

private val ArticleFile.linkDisplay
    get() = meta.name

private val ArticleFile.linkHref
   get() = "${ArticleList.path}/${meta.id}"