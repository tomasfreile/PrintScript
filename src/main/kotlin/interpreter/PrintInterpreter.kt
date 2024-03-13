package org.example.interpreter

import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.Token
import org.example.token.TypeEnum

class PrintInterpreter:Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TypeEnum, Interpreter>,
        symbolTable: Map<String, Token>
    ): Any? {
        if(node is ASTSingleNode){
            println(interpreters[node.node?.token?.type]?.interpret(node.node, interpreters, symbolTable))
        }
        throw UnsupportedOperationException("No value after print")
    }
}