package interpreter.interpreter

import ast.AstNode
import interpreter.variable.Variable

interface Interpreter {
    fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any

    fun canHandle(node: AstNode): Boolean
}
