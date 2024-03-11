package org.example.interpreter

import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.Token
import org.example.token.TypeEnum

class PrintScriptInterpreter : Interpreter {
    override fun interpret(ast: Node?) {
        val symbolTable = emptyMap<String, Token>()
        evaluate(ast, symbolTable)
    }

    private fun evaluate(node: Node?, symbolTable: Map<String, Token>) : Any? {
        if(node == null){
            throw NullPointerException("Evaluate function in interpreter found null node.");
        }
        val token = node.token;
        when(token.type) {
            TypeEnum.PLUS -> return executePlusToken(node, symbolTable)
            TypeEnum.MINUS -> return executeMinusToken(node, symbolTable)
            TypeEnum.STAR -> TODO()
            TypeEnum.SLASH -> TODO()
            TypeEnum.ASSIGNATION -> TODO()
            TypeEnum.SEMICOLON -> TODO()
            TypeEnum.LEFT_PAREN -> return evaluateParen(node, symbolTable)
            TypeEnum.RIGHT_PAREN -> return evaluateParen(node, symbolTable)
            TypeEnum.COLON -> TODO()
            TypeEnum.PRINT -> executePrintToken(node, symbolTable)
            TypeEnum.IF -> TODO()
            TypeEnum.ELSE -> TODO()
            TypeEnum.NUMBER_TYPE -> TODO()
            TypeEnum.STRING_TYPE -> TODO()
            TypeEnum.VARIABLE_KEYWORD -> TODO()
            TypeEnum.VALUE_IDENTIFIER -> TODO()
            TypeEnum.STRING -> return executeStringToken(node, symbolTable)
            TypeEnum.NUMBER -> return executeNumberToken(node, symbolTable)
        }
        return null
    }

    private fun executeStringToken(node: Node, symbolTable: Map<String, Token>): Any? {
        return node.token.value
    }

    private fun executeNumberToken(node: Node, symbolTable: Map<String, Token>): Any? {
        return node.token.value.toInt()
    }

    private fun evaluateParen(node: Node, symbolTable: Map<String, Token>): Any? {
        if(node is ASTSingleNode){
            return evaluate(node.node, symbolTable)
        }
        throw UnsupportedOperationException("No value after paren")
    }

    private fun executePrintToken(node: Node, symbolTable: Map<String, Token>) {
        if(node is ASTSingleNode){
            println(evaluate(node.node, symbolTable))
        }
    }

    private fun executePlusToken(node: Node, symbolTable: Map<String, Token>) : Any {
        if(node is ASTBinaryNode){
            val l = evaluate(node.left, symbolTable)
            val r = evaluate(node.right, symbolTable)
            if(l is Int && r is Int){
                return l + r
            }
            if( l is String){
                return l + r.toString()
            }
            else {
                throw UnsupportedOperationException("Incompatible type for sum")
            }
        }
        else {
            throw UnsupportedOperationException("Missing elements for sum")
        }
    }

    private fun executeMinusToken(node: Node, symbolTable: Map<String, Token>) : Any {
        if(node is ASTBinaryNode){
            val l = evaluate(node.left, symbolTable)
            val r = evaluate(node.right, symbolTable)
            if(l is Int && r is Int){
                return l - r
            }
            else {
                throw UnsupportedOperationException("Incompatible type for sub")
            }
        }
        else {
            throw UnsupportedOperationException("Missing elements for sub")
        }
    }
}