package org.example.interpreter

import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.Token
import org.example.token.TypeEnum

class PrintScriptInterpreter(val interpreters: List<Interpreter>) {
     fun interpret(ast: Node?) {
        val symbolTable = emptyMap<String, Token>()
    }
}
