package visual_interfaces.web.javalinRouting

sealed class Route(name: String, val method: String) {
    companion object {
        val root: String = IPHelper.root
        val startupRouteSet = setOf(
            Home, About
        )
    }
    
    val path = "$root/$name"
    val name = "/$name"
    
    object Home : Route("", "get")
    object About : Route("about", "get")
}
