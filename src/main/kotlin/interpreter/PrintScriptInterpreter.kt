package org.example.interpreter

import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.Token
import org.example.token.TypeEnum

class PrintScriptInterpreter(private val interpreters: Map<TypeEnum, Interpreter>, private val symbolTable: Map<String, Token>) {
     fun interpret(node: Node?) : PrintScriptInterpreter {
         val result = interpreters[node?.token?.type]?.interpret(node, interpreters, symbolTable)
         return if(result is PrintScriptInterpreter){
             result
         } else {
             this
         }
    }
}
