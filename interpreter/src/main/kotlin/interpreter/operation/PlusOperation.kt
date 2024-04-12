package interpreter.operation

import token.TokenType

class PlusOperation : Operation {
    override val symbol = TokenType.PLUS

    override fun resolve(
        l: Any,
        r: Any,
    ): Any {
        if ((l is Int || l is Float) && (r is Int || r is Float)) {
            return when {
                l is Float || r is Float -> (l.toString().toFloat() + r.toString().toFloat()).toFloat()
                else -> l.toString().toInt() + r.toString().toInt()
            }
        }
        if (l is String) {
            return l + r.toString()
        }
        throw UnsupportedOperationException("Unsupported type for plus operation")
    }
}
