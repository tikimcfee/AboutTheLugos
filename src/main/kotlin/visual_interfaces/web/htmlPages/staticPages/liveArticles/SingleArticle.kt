package visual_interfaces.web.htmlPages.staticPages.liveArticles

import io.javalin.http.Context
import visual_interfaces.web.htmlComponents.ComponentClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.div
import visual_interfaces.web.htmlComponents.SimpleHTML.setCssClasses
import visual_interfaces.web.htmlComponents.SimpleHTML.text
import visual_interfaces.web.htmlComponents.Tag
import visual_interfaces.web.htmlPages.MarkdownReader
import visual_interfaces.web.htmlPages.staticPages.liveArticles.LiveArticleLoader
import visual_interfaces.web.javalinRouting.Route

fun Tag.makeArticleContent(context: Context) {
   setCssClasses(ComponentClasses.MainPage.mainBodyWrapper)

   with(LiveArticleLoader) {
      with(MarkdownReader) {
         loadArticleMarkdown(context,
            {
               div {
                  text(createRawHtmlFromMarkdown(it))
               }
            },
            {
               println(it)
               context.redirect(Route.ArticleList.path)
            }
         )
      }
   }
}