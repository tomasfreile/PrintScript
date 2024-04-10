package interpreter

import ast.AstNode
import ast.VariableDeclarationNode
import interpreter.variable.Variable

class DeclarationInterpreter : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as VariableDeclarationNode
        val identifier = node.identifier
        val type = node.valueType
        val value = interpreter.interpret(node.expression, symbolTable)
        symbolTable[Variable(identifier, type)] = value
        return value
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is VariableDeclarationNode
    }
}
