package org.example.parser

import ast.ASTSingleNode
import ast.Node
import parser.Parse
import token.Token
import token.TokenType

class InvalidSyntaxException(message: String): Exception(message)
class PrintParse: Parse {
    override fun parse(tokenList: List<Token>): Node {
        return recursiveParse(tokenList)
    }

    private fun recursiveParse(tokenList: List<Token>): Node{
        val token = tokenList.first()
        return when(token.type){
            TokenType.SEMICOLON -> ASTSingleNode(null, token)
            TokenType.PRINT -> ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token)
            TokenType.LEFT_PAREN -> handleContent(tokenList.subList(1, tokenList.size))
            TokenType.RIGHT_PAREN -> recursiveParse(tokenList.subList(1, tokenList.size))
            else -> throw InvalidSyntaxException("Invalid syntax for print function")
        }
    }

    private fun handleContent(tokenList: List<Token>): Node{
        val token = tokenList.first()
        return when(token.type){
            TokenType.STRING, TokenType.NUMBER -> { handleLiteral(tokenList) }
            else -> { throw InvalidSyntaxException("Invalid syntax, should print String") }
        }
    }

    private fun isConcatenate(tokenList: List<Token>): Boolean{
        return tokenList.first().type == TokenType.PLUS
    }

    private fun handleLiteral(tokenList: List<Token>): Node{
        return ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), tokenList.first())
    }
}