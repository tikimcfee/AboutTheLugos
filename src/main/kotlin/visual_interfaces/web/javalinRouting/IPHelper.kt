package visual_interfaces.web.javalinRouting

import java.net.InetAddress

object IPHelper {
    const val unsafeProtocolHttp = "http"
    const val encryptedProtocolHttps = "https"

    const val preferredUnsafeHttpPort = 8080
    const val preferredEncryptedHttpsPort = 8443
    
    val localNetworkIp: String
        get() = InetAddress.getLocalHost()?.hostAddress ?: "localhost"
    
    val unsafeRoot = "${unsafeProtocolHttp}://${localNetworkIp}:${preferredUnsafeHttpPort}"
    val encryptedRoot = "${encryptedProtocolHttps}://${localNetworkIp}:${preferredEncryptedHttpsPort}"
}
