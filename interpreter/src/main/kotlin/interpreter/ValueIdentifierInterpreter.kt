package interpreter

import ast.ASTSingleNode
import ast.Node
import interpreter.exception.UnresolvedReferenceException
import token.Token
import token.TokenType

class ValueIdentifierInterpreter : Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>,
    ): Any {
        node as ASTSingleNode
        val valueIdentifier = node.token.value
        val value = symbolTable[valueIdentifier]?.value ?: throw UnresolvedReferenceException("Variable $valueIdentifier not declared")
        return value
    }
}
