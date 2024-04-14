package interpreter.function

import ast.FunctionNode
import interpreter.PrintScriptInterpreter
import interpreter.variable.Variable
import token.TokenType

interface Function {
    val type: TokenType

    fun run(
        interpreter: PrintScriptInterpreter,
        node: FunctionNode,
        symbolTable: MutableMap<Variable, Any>,
    ): Any

    fun canHandle(nodeType: TokenType): Boolean
}
