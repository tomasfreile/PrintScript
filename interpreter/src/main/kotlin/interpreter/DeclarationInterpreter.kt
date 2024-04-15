package interpreter

import ast.AstNode
import ast.VariableDeclarationNode
import interpreter.result.Result
import interpreter.variable.Variable

class DeclarationInterpreter : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as VariableDeclarationNode
        val identifier = node.identifier
        val valueType = node.valueType
        val variableType = node.declarationType
        val value = interpreter.interpret(node.expression, symbolTable)
        symbolTable[Variable(identifier, valueType, variableType)] = value
        return Result(value)
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is VariableDeclarationNode
    }
}
