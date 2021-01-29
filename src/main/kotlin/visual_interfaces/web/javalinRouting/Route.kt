package visual_interfaces.web.javalinRouting

sealed class Route(name: String, val method: String) {
    companion object {
        val root: String = IPHelper.root
        val startupRouteSet = setOf(
            Root, Home, About
        )
    }
    
    val path = "$root/$name"
    val name = "/$name"
    
    object Root : Route("", "get")
    object Home : Route("home", "get")
    object About : Route("about", "get")
}
