package visual_interfaces.web.htmlComponents

open class Tag(val name: String) {
    val children = mutableListOf<Tag>()
    val attributes = mutableListOf<Attribute>()
    override fun toString(): String {
        return "<$name" +
            (if (attributes.isEmpty()) "" else attributes.joinToString(separator = " ", prefix = " ")) + ">" +
            (if (children.isEmpty()) "" else children.joinToString(separator = "")) +
            "</$name>"
    }
}
