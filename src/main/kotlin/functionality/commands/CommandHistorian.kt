package functionality.commands

import java.io.File

class CommandHistorian {
    
    private val historyDirectoryName = "_working"
    private val commandHistoryName = "command_history"
    private val commandHistoryBackupPre = "command_history_pre.bak"
    private val commandHistoryBackupPost = "command_history_post.bak"
    
    fun recordCommand(command: Command) {
        with(CommandProcessor) {
            recordRawCommand(command.toJsonString())
        }
    }
    
    fun recordRawCommand(rawCommand: String) {
        println("Recording raw command: [$rawCommand]")
        with(historyFile()) {
//            copyTo(preBackupFile(), overwrite = true)
            appendText(rawCommand + "\n")
//            copyTo(postBackupFile(), overwrite = true)
        }
    }
    
    fun readCommandHistory() = historyFile().readLines()
    
    private fun ensureRoot() =
        File(workingDirectory())
            .resolve(historyDirectoryName)
            .also { if (!it.exists()) it.mkdirs() }
    
    private fun File.ensureExists() = apply { if (!exists()) createNewFile() }
    
    private fun ensureFile(name: String) = ensureRoot().resolve(name).ensureExists()
    
    private fun historyFile() = ensureFile(commandHistoryName)
    
    private fun preBackupFile() = ensureFile(commandHistoryBackupPre)
    
    private fun postBackupFile() = ensureFile(commandHistoryBackupPost)
    
    private fun workingDirectory() = System.getProperty("user.dir")
}
