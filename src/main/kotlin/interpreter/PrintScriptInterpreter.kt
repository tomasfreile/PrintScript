package org.example.interpreter

import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.Token
import org.example.token.TypeEnum

class PrintScriptInterpreter(private val interpreters: Map<TypeEnum, Interpreter>) {
     fun interpret(node: Node?) {
        val symbolTable = emptyMap<String, Token>()
         interpreters[node?.token?.type]?.interpret(node, interpreters, symbolTable)
    }
}
