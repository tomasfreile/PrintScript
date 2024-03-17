package org.example.parser

import org.example.token.Token
import org.example.token.TypeEnum

class InvalidSyntaxException(message: String): Exception(message)
class PrintParse: Parse{
    override fun parse(tokenList: List<Token>): Node {
        return recursiveParse(tokenList)
    }

    private fun recursiveParse(tokenList: List<Token>): Node{
        val token = tokenList.first()
        return when(token.type){
            TypeEnum.SEMICOLON -> ASTSingleNode(null, token)
            TypeEnum.PRINT -> ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token)
            TypeEnum.LEFT_PAREN -> handleContent(tokenList.subList(1, tokenList.size))
            TypeEnum.RIGHT_PAREN -> recursiveParse(tokenList.subList(1, tokenList.size))
            else -> throw InvalidSyntaxException("Invalid syntax for print function")
        }
    }

    private fun handleContent(tokenList: List<Token>): Node{
        val token = tokenList.first()
        return when(token.type){
            TypeEnum.STRING, TypeEnum.NUMBER -> { handleLiteral(tokenList) }
            else -> { throw InvalidSyntaxException("Invalid syntax, should print String") }
        }
    }

    private fun isConcatenate(tokenList: List<Token>): Boolean{
        return tokenList.first().type == TypeEnum.PLUS
    }

    private fun handleLiteral(tokenList: List<Token>): Node{
        return ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), tokenList.first())
    }
}