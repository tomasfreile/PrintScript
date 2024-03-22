package org.example.interpreter

import jdk.jshell.UnresolvedReferenceException
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.Token
import org.example.token.TokenType

class ValueIdentifierInterpreter:Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>
    ): Any? {
        node as ASTSingleNode
        val valueIdentifier = node.token.value
        val value = symbolTable[valueIdentifier]?.value ?: throw org.example.interpreter.exception.UnresolvedReferenceException("Variable $valueIdentifier not declared")
        return value
    }
}