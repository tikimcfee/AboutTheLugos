package visual_interfaces.web.htmlPages.staticPages

import visual_interfaces.web.htmlComponents.ComponentClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.div
import visual_interfaces.web.htmlComponents.SimpleHTML.link
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.Tag
import visual_interfaces.web.htmlPages.liveArticles.ArticleFile
import visual_interfaces.web.htmlPages.liveArticles.LiveArticleLoader
import visual_interfaces.web.javalinRouting.Route.ArticleList

fun Tag.makeArticleListContent() {
    setCssClasses(ComponentClasses.MainPage.mainBodyWrapper)

    with(LiveArticleLoader) {
        articleList.forEach {
            div {
                link(it.linkHref, it.linkDisplay)
            }
        }
    }
}

private val ArticleFile.linkDisplay
    get() = meta.name

private val ArticleFile.linkHref
   get() = "${ArticleList.path}/${meta.id}"