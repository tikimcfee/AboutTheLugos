package visual_interfaces.web.javalinRouting

import java.net.InetAddress

object IPHelper {
    private const val protocol = "http"
    
    val preferredPort = "7000".toInt()
    
    val localNetworkIp: String
        get() = InetAddress.getLocalHost()?.hostAddress ?: "localhost"
    
    val root = "${protocol}://${localNetworkIp}:${preferredPort}"
}
