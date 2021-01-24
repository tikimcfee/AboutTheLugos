package visual_interfaces.web.javalinRouting

sealed class Route(name: String, val method: String) {
    companion object {
        const val port: String = IPHelper.preferredPort
        val host: String = IPHelper.localNetworkIp
        val protocol: String = IPHelper.protocol
        val root: String = "$protocol://$host:$port"
        
        val startupRouteSet = setOf(
            Home, About
        )
    }
    
    val path = "$root/$name"
    val name = "/$name"
    
    object Home : Route("", "get")
    object About : Route("about", "get")
}
