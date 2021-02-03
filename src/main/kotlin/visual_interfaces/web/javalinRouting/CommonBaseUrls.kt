package visual_interfaces.web.javalinRouting

object CommonBaseUrls {
    val root: String = IPHelper.root
    const val publicResourcePath = "/public"
}

enum class Route(
    pathName: String,
    val method: String
) {

    Root("", "get"),
    Home("home", "get"),
    About("about", "get"),
    ArticleList("articles", "get"),
    SingleArticle("articles/:articleid", "get");

    val absolutePath = "${CommonBaseUrls.root}/$pathName"
    val path = "/$pathName"
}