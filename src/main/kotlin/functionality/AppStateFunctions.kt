package functionality

import functionality.commands.Command


class AppStateFunctions(

) {
    
    fun saveAndRun(command: Command) {
    
    }
    
}

fun Command.executeWithStateFunctions(
    appStateFunctions: AppStateFunctions
): Any = when (this) {
    else -> { }
}

fun AppStateFunctions.restoreState() {
    println("--- Restoring App State ---")
    
}
