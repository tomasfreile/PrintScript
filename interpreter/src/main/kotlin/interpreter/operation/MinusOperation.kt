package interpreter.operation

import token.TokenType

class MinusOperation : Operation {
    override val symbol = TokenType.MINUS

    override fun resolve(
        l: Any,
        r: Any,
    ): Any {
        if ((l is Int || l is Float) && (r is Int || r is Float)) {
            return when {
                l is Float || r is Float -> (l.toString().toFloat() - r.toString().toFloat()).toFloat()
                else -> l.toString().toInt() - r.toString().toInt()
            }
        }
        throw UnsupportedOperationException("Unsupported type for multiplication operation")
    }
}
