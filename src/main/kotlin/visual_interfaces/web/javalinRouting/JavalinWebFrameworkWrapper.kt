package visual_interfaces.web.javalinRouting

import visual_interfaces.web.htmlPages.MarkdownReader
import visual_interfaces.web.javalinRouting.serverBuilding.JavalinServerBootstrapper
import visual_interfaces.web.javalinRouting.serverBuilding.applicationStarted

fun main(args: Array<String>) {
//    when {
//        args.contains("test-things") ->
//            TestingWrapper.testTheThings()
//        else ->
//            JavalinServer().start()
//    }
    JavalinServerBootstrapper().applicationStarted()
}

object TestingWrapper {
    fun testTheThings() {
        MarkdownReader.createRawHtmlFromMarkdown("_He_*ll*o, *world!*")
    }
}