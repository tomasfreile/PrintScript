package interpreter

import ast.AstNode
import ast.BinaryOperationNode
import interpreter.operation.Operation
import interpreter.variable.Variable

class OperationInterpreter(private val operations: List<Operation>) : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as BinaryOperationNode
        val l = interpreter.interpret(node.left, symbolTable)
        val r = interpreter.interpret(node.right, symbolTable)
        for (operation in operations) {
            if (operation.symbol == node.operator) {
                return operation.resolve(l, r)
            }
        }
        throw UnsupportedOperationException("Operation interpreter does not have a resolver for symbol: " + node.operator)
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is BinaryOperationNode
    }
}
