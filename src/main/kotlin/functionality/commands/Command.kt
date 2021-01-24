package functionality.commands

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val MAIN_APP_STOP = "--EndOfLine--"

@Serializable
sealed class Command() {
    
    @Serializable
    @SerialName(MAIN_APP_STOP)
    object MainAppStop : Command()
    
}

