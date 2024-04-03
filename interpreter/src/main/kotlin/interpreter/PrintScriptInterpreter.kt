package interpreter

import ast.Node
import token.Token
import token.TokenType

class PrintScriptInterpreter(private val interpreters: Map<TokenType, Interpreter>, val symbolTable: Map<String, Token>) {
    fun interpret(node: Node?): PrintScriptInterpreter {
        val result = interpreters[node?.token?.type]?.interpret(node, interpreters, symbolTable)
        return if (result is PrintScriptInterpreter) {
            result
        } else {
            this
        }
    }
}
