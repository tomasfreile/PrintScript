package interpreter.literal

import ast.LiteralNode
import token.TokenType

interface Literal {
    val type: TokenType

    fun eval(node: LiteralNode): Any
}
