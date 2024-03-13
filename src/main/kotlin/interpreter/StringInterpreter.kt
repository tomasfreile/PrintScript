package org.example.interpreter

import org.example.parser.Node
import org.example.token.Token
import org.example.token.TypeEnum

class StringInterpreter: Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TypeEnum, Interpreter>,
        symbolTable: Map<String, Token>
    ): Any? {
        return node?.token?.value
    }
}