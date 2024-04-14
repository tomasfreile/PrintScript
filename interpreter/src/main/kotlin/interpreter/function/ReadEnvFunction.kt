package interpreter.function

import ast.FunctionNode
import interpreter.PrintScriptInterpreter
import interpreter.variable.Variable
import token.TokenType

class ReadEnvFunction : Function {
    override val type: TokenType = TokenType.READ_ENV

    override fun run(
        interpreter: PrintScriptInterpreter,
        node: FunctionNode,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        return interpreter.interpret(node.expression, symbolTable)
    }

    override fun canHandle(nodeType: TokenType): Boolean {
        return nodeType == type
    }
}
