package interpreter.literal

import ast.LiteralNode
import token.TokenType

class String : Literal {
    override val type: TokenType = TokenType.STRING_LITERAL

    override fun eval(node: LiteralNode): Any {
        return node.value
    }
}
