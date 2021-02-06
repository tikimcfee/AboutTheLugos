package visual_interfaces.web.javalinRouting

import visual_interfaces.web.htmlPages.MarkdownReader

fun main(args: Array<String>) {
//    when {
//        args.contains("test-things") ->
//            TestingWrapper.testTheThings()
//        else ->
//            JavalinServer().start()
//    }
    JavalinServer().start()
}

object TestingWrapper {
    fun testTheThings() {
        MarkdownReader.createRawHtmlFromMarkdown("_He_*ll*o, *world!*")
    }
}