package visual_interfaces.web.htmlPages

import io.javalin.http.Context
import kotlinx.css.CSSBuilder
import visual_interfaces.web.htmlComponents.ComponentClasses.Shared
import visual_interfaces.web.htmlComponents.SimpleHTML
import visual_interfaces.web.htmlComponents.SimpleHTML.Html
import visual_interfaces.web.htmlComponents.SimpleHTML.meta
import visual_interfaces.web.htmlComponents.SimpleHTML.setAttribute
import visual_interfaces.web.htmlComponents.Tag
import visual_interfaces.web.htmlPages.staticPages.aboutPageStyles
import visual_interfaces.web.htmlPages.staticPages.liveArticles.articleListPageStyles
import visual_interfaces.web.htmlPages.staticPages.makeAboutPageContent
import visual_interfaces.web.htmlPages.staticPages.liveArticles.makeArticleContent
import visual_interfaces.web.htmlPages.staticPages.liveArticles.makeArticleListContent
import visual_interfaces.web.htmlPages.staticPages.makeHomePageContent
import visual_interfaces.web.javalinRouting.Route

object RouteRenderer {

    internal val navigationRoutes by lazy { arrayOf(
       Route.Home,
       Route.About,
       Route.ArticleList
    ) }

    fun Context.renderPageAt(route: Route) {
        renderHtml(
           inSharedPageFrame(route) {
               when (route) {
                   Route.Home -> makeHomePageContent()
                   Route.About -> makeAboutPageContent()
                   Route.ArticleList -> makeArticleListContent()
                   Route.SingleArticle -> makeArticleContent(this@renderPageAt)
                   else -> { }
               }
           }
        )
    }
}

private fun inSharedPageFrame(
    currentRoute: Route,
    builder: Tag.() -> Unit
) = with(SimpleHTML) {
    html {
        setMetaData()
        head {
            style {
                text(
                   CSSBuilder().apply {
                       globalStyleBuilder()
                       currentRoute.styleBuilder(this)
                   }.toString()
                )
            }
        }

        body {
            setCssClasses(Shared.topContentContainer)
            div {
                setCssClasses(Shared.staticNavigationBar)
                RouteRenderer.navigationRoutes.forEach { route ->
                    link(route.path, route.displayName) {
                        setCssClasses(route.anchorSelectionClass(currentRoute))
                    }
                }
            }
            div {
                builder()
            }
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

private val Route.styleBuilder: CSSBuilder.() -> Unit
    get() = when (this) {
        Route.ArticleList -> articleListPageStyles
        Route.About -> aboutPageStyles
        else -> { {} } // empty css builder
    }

private val Route.displayName: String
    get() = when (this) {
        Route.Home -> "Home"
        Route.About -> "About"
        Route.ArticleList -> "Articles"
        Route.SingleArticle -> "Single Article"
        Route.Root -> "root - r u a wizrd?"
    }

private fun Route.anchorSelectionClass(
    currentRoute: Route
) = when (currentRoute) {
    this -> Shared.staticNavigationBarAnchorCurrent
    else -> Shared.staticNavigationBarAnchorOther
}

private fun Context.setTextResult(text: String) =
   header("Content-Type",  "text/html").result(text)

private fun Context.renderHtml(html: Html) =
   setTextResult(html.toString())