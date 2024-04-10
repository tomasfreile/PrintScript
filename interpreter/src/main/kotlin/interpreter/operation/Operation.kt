package interpreter.operation

import token.TokenType

interface Operation {
    val symbol: TokenType

    fun resolve(
        l: Any,
        r: Any,
    ): Any
}
