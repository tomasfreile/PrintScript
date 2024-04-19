package interpreter.function

import ast.FunctionNode
import interpreter.interpreter.PrintScriptInterpreter
import interpreter.result.PrintResult
import interpreter.variable.Variable
import token.TokenType

class ReadInputFunction : Function {
    override val type: TokenType = TokenType.READINPUT

    override fun run(
        interpreter: PrintScriptInterpreter,
        node: FunctionNode,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        val prompt: String = interpreter.interpret(node.expression, symbolTable).toString()
        return PrintResult(prompt)
    }

    override fun canHandle(nodeType: TokenType): Boolean {
        return nodeType == type
    }
}
