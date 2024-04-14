package interpreter.literal

import ast.LiteralNode
import token.TokenType

class Number : Literal {
    override val type: TokenType = TokenType.NUMBER_LITERAL

    override fun eval(node: LiteralNode): Any {
        if (node.value.contains(".")) {
            return node.value.toFloat()
        }
        return node.value.toInt()
    }
}
