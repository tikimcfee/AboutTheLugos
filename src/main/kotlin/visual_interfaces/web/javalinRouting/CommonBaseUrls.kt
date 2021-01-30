package visual_interfaces.web.javalinRouting

object CommonBaseUrls {
    val root: String = IPHelper.root
}

enum class Route(
    pathName: String,
    val method: String
) {

    Root("", "get"),
    Home("home", "get"),
    About("about", "get");

    val absolutePath = "${CommonBaseUrls.root}/$pathName"
    val path = "/$pathName"
}