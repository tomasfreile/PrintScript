package interpreter.function

import ast.FunctionNode
import interpreter.PrintScriptInterpreter
import interpreter.variable.Variable
import token.TokenType

class ReadInputFunction : Function {
    override val type: TokenType = TokenType.READ_INPUT

    override fun run(
        interpreter: PrintScriptInterpreter,
        node: FunctionNode,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        val input = readLine() ?: throw NullPointerException("input can't be null.")
        return input
    }

    override fun canHandle(nodeType: TokenType): Boolean {
        return nodeType == type
    }
}
