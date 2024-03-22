package org.example.interpreter

import org.example.parser.Node
import org.example.token.Token
import org.example.token.TokenType

interface Interpreter {
    fun interpret(node: Node?, interpreters: Map<TokenType, Interpreter>, symbolTable: Map<String, Token>): Any?
}