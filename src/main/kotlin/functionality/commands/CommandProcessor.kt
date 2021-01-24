package functionality.commands

import kotlinx.serialization.*
import kotlinx.serialization.json.*

object CommandProcessor {
    
    fun parseStringCommand(input: String?) =
        input?.let {
            Json.decodeFromString<Command>(it)
        } ?: Command.MainAppStop
    
    fun Command.toJsonString(): String =
        Json.encodeToString(this)
}
