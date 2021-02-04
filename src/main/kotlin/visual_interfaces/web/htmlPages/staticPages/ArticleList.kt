package visual_interfaces.web.htmlPages.staticPages

import kotlinx.css.BorderStyle
import kotlinx.css.BorderStyle.solid
import kotlinx.css.CSSBuilder
import kotlinx.css.Color.Companion.gray
import kotlinx.css.Color.Companion.white
import kotlinx.css.FontWeight
import kotlinx.css.borderColor
import kotlinx.css.borderRadius
import kotlinx.css.borderStyle
import kotlinx.css.borderWidth
import kotlinx.css.color
import kotlinx.css.fontWeight
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.properties.TextDecoration.Companion.none
import kotlinx.css.px
import kotlinx.css.textDecoration
import visual_interfaces.web.htmlComponents.ComponentClasses
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.link
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkContainer
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkHover
import visual_interfaces.web.htmlComponents.ComponentClasses.ArticleList.linkVisited
import visual_interfaces.web.htmlComponents.SimpleHTML.div
import visual_interfaces.web.htmlComponents.SimpleHTML.h3
import visual_interfaces.web.htmlComponents.SimpleHTML.h4
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.spannedLink
import visual_interfaces.web.htmlComponents.SimpleHTML.text
import visual_interfaces.web.htmlComponents.Tag
import visual_interfaces.web.htmlComponents.addClass
import visual_interfaces.web.htmlPages.ColorPalette.articleListLinkColor
import visual_interfaces.web.htmlPages.ColorPalette.articleListLinkContainerBorder
import visual_interfaces.web.htmlPages.liveArticles.ArticleFile
import visual_interfaces.web.htmlPages.liveArticles.LiveArticleLoader
import visual_interfaces.web.javalinRouting.Route.ArticleList

fun Tag.makeArticleListContent() {
    setCssClasses(ComponentClasses.MainPage.mainBodyWrapper)

    h3 { text("Index of Readable Things") }
    h4 { text("I wrote this stuff for some reason. Feel free to consume the media.") }

    with(LiveArticleLoader) {
        articleList.forEach {
            div {
                spannedLink(it.linkHref, it.linkDisplay,
                   spanBuilder = { setCssClasses(linkContainer) },
                   anchorBuilder = { setCssClasses(link, linkHover, linkVisited) }
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
    addClass(linkContainer) {
        margin = "4px"
        padding = "8px 8px 8px 4px"
        borderStyle = solid
        borderWidth = 1.px
        borderRadius = 4.px
        borderColor = articleListLinkContainerBorder
    }
}

private val ArticleFile.linkDisplay
    get() = meta.name

private val ArticleFile.linkHref
   get() = "${ArticleList.path}/${meta.id}"