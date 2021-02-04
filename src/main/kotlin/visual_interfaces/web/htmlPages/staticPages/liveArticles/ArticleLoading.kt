package visual_interfaces.web.htmlPages.staticPages.liveArticles

import io.javalin.http.Context
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Path

object LiveArticleLoader {
   var articleList: List<ArticleFile> = emptyList()
      private set
   var articleMap: Map<String, ArticleFile> = emptyMap()
      private set

   private val loadingThread: ArticleLoadingThread = ArticleLoadingThread { articles ->
      articleList = articles
      articleMap = articles.associateBy { it.meta.id }
   }

   fun beginArticleObservation() {
      // This is so fragile it's funny. Since we're not a UI app with draw conditions,
      // articles can be (re)loaded at any time and are available as soon as they are
      // 'indexed' as a result from of this thread completing a cycle
      loadingThread.start()
   }

   fun loadArticleMarkdown(context: Context, markdownReceiver: (String) -> Unit) {
      val articleIdParam = context.pathParam("articleid")
      if (articleIdParam.isBlank()) {
         println("No articleid for $context")
         return
      }
      articleMap[articleIdParam]?.readArticleContents()?.let {
         markdownReceiver(it)
      } ?: println("Failed to load markdown for $context")
   }
}

class ArticleLoadingThread(
   var listener: (List<ArticleFile>) -> Unit
): Thread() {

   private val reloadDelay = 600_000L // One hour
   init {
      isDaemon = true
      name = "Article-Scanner-3000"
   }

   override fun run() {
      while(!isInterrupted) {
         println("$this querying local articles...")
         listener(load())
         sleep(reloadDelay)
      }
   }

   private fun load(): List<ArticleFile> {
      val articles = mutableListOf<ArticleFile>()
      ArticleFileSystemTools.walkArticles { articleDirectoryFilePath ->
         with(ArticleSniffState()) loadArticleFromDir@{
            articleDirectoryFilePath.forEach { filePath ->
               println("Walking $filePath")
               consumePath(filePath)
               makeArticleFile()?.let { article ->
                  println("Loaded article: $article")
                  articles.add(article)
                  return@loadArticleFromDir
               }
            }
         }
      }
      return articles
   }
}

data class ArticleSniffState(
   var mainMarkdownPath: Path? = null,
   var metaPath: Path? = null
) {
   private fun String.isMarkdown() = endsWith(".md")
   private fun String.isMeta() = endsWith("meta.json")

   fun consumePath(path: Path) {
      val absolutePath = path.toString()
      when {
         absolutePath.isMarkdown() -> mainMarkdownPath = path
         absolutePath.isMeta() -> metaPath = path
      }
   }

   fun makeArticleFile(): ArticleFile? {
      return metaPath?.let { meta ->
         return mainMarkdownPath?.let { markdown ->
            return try {
               val jsonRaw = String(meta.toFile().readBytes())
               val decodedMeta = Json.decodeFromString<ArticleMeta>(jsonRaw)
               ArticleFile(decodedMeta, markdown)
            } catch (error: Exception) {
               println(error)
               null
            }
         }
      }
   }
}