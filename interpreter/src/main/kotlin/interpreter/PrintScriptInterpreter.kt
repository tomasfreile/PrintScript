package interpreter

import ast.AstNode
import interpreter.variable.Variable

class PrintScriptInterpreter(private val interpreters: List<Interpreter>) {
    fun interpret(
        node: AstNode?,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        if (node == null) {
            throw NullPointerException("Node is null.")
        }
        for (interpreter in interpreters) {
            if (interpreter.canHandle(node)) {
                return interpreter.interpret(node, this, symbolTable)
            }
        }
        throw UnsupportedOperationException("Unkown node type ${node.javaClass}")
    }
}
