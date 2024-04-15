package interpreter

import ast.AssignmentNode
import ast.AstNode
import interpreter.result.Result
import interpreter.variable.Variable
import token.TokenType

class AssignationInterpreter : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as AssignmentNode
        val identifier = node.identifier
        val variable: Variable = getVariable(symbolTable, identifier)
        if (variable.declarationType != TokenType.LET) {
            throw UnsupportedOperationException(
                "Cannot assign a constant.  In position " + node.position.start.string() + ":" + node.position.end.string(),
            )
        }
        val value = interpreter.interpret(node.expression, symbolTable)
        symbolTable[variable] = value
        return Result(value)
    }

    private fun getVariable(
        symbolTable: MutableMap<Variable, Any>,
        identifier: String,
    ): Variable {
        for (variableSymbol in symbolTable.keys) {
            if (variableSymbol.name == identifier) {
                return variableSymbol
            }
        }
        throw NullPointerException("Variable not declared")
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is AssignmentNode
    }
}
