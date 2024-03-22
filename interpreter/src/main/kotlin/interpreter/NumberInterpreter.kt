package org.example.interpreter

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType

class NumberInterpreter: Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>
    ): Any? {
        return node?.token?.value?.toInt()
    }
}