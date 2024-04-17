package interpreter.literal

import ast.LiteralNode
import token.TokenType

class Boolean() : Literal {
    override val type: TokenType = TokenType.BOOLEAN_LITERAL

    override fun eval(node: LiteralNode): Any {
        return node.value.toBoolean()
    }
}
