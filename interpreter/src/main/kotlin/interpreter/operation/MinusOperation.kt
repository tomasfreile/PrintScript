package interpreter.operation

class MinusOperation : Operation {
    override val symbol = "-"

    override fun resolve(
        l: Any?,
        r: Any?,
    ): Any? {
        if (l is Int && r is Int) {
            return l - r
        }
        throw UnsupportedOperationException("Unsupported type for plus operation")
    }
}
