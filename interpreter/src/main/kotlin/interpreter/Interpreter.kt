package interpreter

import ast.Node
import token.Token
import token.TokenType

interface Interpreter {
    fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>,
    ): Any?
}
