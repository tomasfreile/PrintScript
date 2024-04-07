package interpreter.operation

interface Operation {
    val symbol: String

    fun resolve(
        l: Any?,
        r: Any?,
    ): Any?
}
