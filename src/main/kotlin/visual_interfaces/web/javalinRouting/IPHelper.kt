package visual_interfaces.web.javalinRouting

import java.net.DatagramSocket
import java.net.InetAddress


object IPHelper {
    const val unsafeProtocolHttp = "http"
    const val encryptedProtocolHttps = "https"

    const val preferredUnsafeHttpPort = 8080
    const val preferredEncryptedHttpsPort = 8443

    val localNetworkIp: String
        get() = "0.0.0.0"
    
    val localNetworkIpInet: String
        get() = InetAddress.getLocalHost()?.hostAddress ?: "localhost"

    fun datagramNetworkIp(): String {
        return DatagramSocket().use { socket ->
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002)
            socket.localAddress.hostAddress
        }
    }
    
    val unsafeRoot = "${unsafeProtocolHttp}://${localNetworkIp}:${preferredUnsafeHttpPort}"
    val encryptedRoot = "${encryptedProtocolHttps}://${localNetworkIp}:${preferredEncryptedHttpsPort}"
}
