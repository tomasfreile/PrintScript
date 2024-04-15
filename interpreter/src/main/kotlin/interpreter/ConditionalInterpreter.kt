package interpreter

import ast.AstNode
import ast.IfNode
import interpreter.variable.Variable

class ConditionalInterpreter : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as IfNode
        val conditionValue = interpreter.interpret(node.condition, symbolTable) as Boolean
        return if (conditionValue) {
            interpreter.interpret(node.thenBlock, symbolTable)
        } else {
            interpreter.interpret(node.elseBlock, symbolTable)
        }
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is IfNode
    }
}
