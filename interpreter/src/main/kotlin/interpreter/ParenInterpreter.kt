package org.example.interpreter

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType

class ParenInterpreter:Interpreter {

    override fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>
    ): Any? {
        if (node is ASTSingleNode) {
            return interpreters[node.node?.token?.type]?.interpret(node.node, interpreters, symbolTable)
        }
        throw UnsupportedOperationException("No value after paren")
    }
}