package org.example.interpreter

import org.example.interpreter.operation.Operation
import org.example.parser.ASTBinaryNode
import org.example.parser.Node
import org.example.token.Token
import org.example.token.TokenType

class OperationInterpreter(private val operations: List<Operation>) : Interpreter {
    override fun interpret(node: Node?, interpreters: Map<TokenType, Interpreter>, symbolTable: Map<String, Token>): Any?
    {
        if (node == null) throw NullPointerException()
        val token = node.token
        node as ASTBinaryNode
        if( node.left == null || node.right == null) throw NullPointerException()
        val l = interpreters[node.left.token.type]?.interpret(node.left, interpreters, symbolTable)
        val r = interpreters[node.right.token.type]?.interpret(node.right, interpreters, symbolTable)
        for(operation in operations){
            if(operation.symbol == node.token.value){
                return operation.resolve(l, r)
            }
        }
        throw UnsupportedOperationException("Operation interpreter does not have a resolver for symbol: " + node.token.value)

    }
}