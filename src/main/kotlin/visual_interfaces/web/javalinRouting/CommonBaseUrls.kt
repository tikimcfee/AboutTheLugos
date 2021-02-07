package visual_interfaces.web.javalinRouting

import java.nio.file.Paths

object CommonBaseUrls {
    val root: String = IPHelper.unsafeRoot

    val publicResourcePath =
       System.getProperty("user.dir")
          .let { Paths.get(it) }
          .resolve("public")
          .toAbsolutePath()
          .toString()

    // This needs to load dynamically somehow... oof.
    // Or just serve the thing properly. Either way.
    const val acmeWellKnownPath = "/.well-known/acme-challenge/:id"
    const val acmeWellKnownPathId = "id"
    const val acmePersonalId = "DZUjlzB8RL8iBxOmvenqRYI_zhdXJYy8Ic1Z5hf6PlM"
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