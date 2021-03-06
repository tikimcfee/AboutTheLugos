package visual_interfaces.web.htmlPages.staticPages.liveArticles

import kotlinx.serialization.Serializable
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.streams.toList

@Serializable
data class ArticleMeta(
   val name: String,
   val id: String,
   val summary: String
)

data class ArticleFile(
   val meta: ArticleMeta,
   val fileSystemPath: Path
)

fun ArticleFile.readArticleContents(): String {
   return try {
      String(Files.readAllBytes(fileSystemPath))
   } catch (e: IOException) {
      println(e)
      "Sorry, I broke something! Or someone did! I'LL FIND THE RIGHT PERSON TO BLAME FOR THIS!"
   }
}

object ArticleFileSystemTools {
   private fun mainArticleContainerDirectories() =
      System.getProperty("user.dir")
         .let { Paths.get(it) }
         .resolve("public")
         .resolve("articles")
         .let { Files.list(it) }
         .filter { it.toFile().isDirectory }

   private fun articleFiles(articleDirectoryPath: Path) =
      articleDirectoryPath
         .let { Files.list(it) }
         .filter { it.toFile().isFile }

   fun walkArticles(articleGroup: (List<Path>) -> Unit) =
      mainArticleContainerDirectories()
         .map { articleFiles(it) }
         .map { it.toList() }
         .forEach { articleGroup(it) }
}