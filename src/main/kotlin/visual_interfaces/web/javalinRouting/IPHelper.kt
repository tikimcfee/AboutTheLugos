package visual_interfaces.web.javalinRouting

import java.net.InetAddress

object IPHelper {
    const val preferredPort = "7000"
    const val protocol = "http"
    val localNetworkIp: String
        get() = InetAddress.getLocalHost()?.hostAddress ?: "localhost"
}
