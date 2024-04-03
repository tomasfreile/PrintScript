package interpreter

import ast.ASTBinaryNode
import ast.Node
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType

class AssignationInterpreter : Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>,
    ): Any? {
        node as ASTBinaryNode
        val valueIdentifier = node.left?.token?.value ?: throw NullPointerException("valueIdentifier is null")
        val value =
            interpreters[node.right?.token?.type]?.interpret(
                node.right,
                interpreters,
                symbolTable,
            ) ?: throw NullPointerException("Cant assing null to $valueIdentifier")
        val oldToken = symbolTable[valueIdentifier] ?: throw NullPointerException()
        val token = PrintScriptToken(oldToken.type, value.toString(), Coordinate(0, 0), Coordinate(0, 0))
        val newSymbolTable = symbolTable.plus(Pair(valueIdentifier, token))
        return PrintScriptInterpreter(interpreters, newSymbolTable)
    }
}
