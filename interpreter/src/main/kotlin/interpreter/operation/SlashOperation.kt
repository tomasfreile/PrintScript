package interpreter.operation

import token.TokenType

class SlashOperation : Operation {
    override val symbol = TokenType.SLASH

    override fun resolve(
        l: Any,
        r: Any,
    ): Any {
        if (l is Int && r is Int) {
            return l / r
        }
        throw UnsupportedOperationException("Unsupported type for plus operation")
    }
}
