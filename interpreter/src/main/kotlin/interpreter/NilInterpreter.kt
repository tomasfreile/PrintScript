package interpreter

import ast.AstNode
import ast.NilNode
import interpreter.variable.Variable

class NilInterpreter : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as NilNode
        return node
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is NilNode
    }
}
