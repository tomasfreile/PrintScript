package interpreter

import ast.Node
import token.Token
import token.TokenType

class NumberInterpreter : Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>,
    ): Any? {
        return node?.token?.value?.toInt()
    }
}
