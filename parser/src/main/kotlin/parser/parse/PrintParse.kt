package org.example.parser

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import parser.InvalidSyntax
import parser.parse.Parse
import parser.sintactic.commons.hasCombination
import token.Token
import token.TokenType

class PrintParse: Parse {
    override fun parse(tokenList: List<Token>): Node {
        val token = tokenList.first()
        return when(token.type){
            TokenType.STAR, TokenType.SLASH, TokenType.MINUS -> throw InvalidSyntax("Invalid operator")
            TokenType.SEMICOLON -> ASTSingleNode(null, token) //break
            TokenType.PRINT -> {
                if(hasCombination().checkSyntax(tokenList)){
                    ASTSingleNode(buildBinaryNode(tokenList.subList(1, tokenList.size)), token)
                }else{
                    ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token)
                }
            }
            TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN -> parse(tokenList.subList(1, tokenList.size))
            else -> ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token) //i dont know what you are
        }
    }

    private fun buildBinaryNode(tokenList: List<Token>): Node{
        val token = tokenList.first()
        return when(token.type){
            TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN -> buildBinaryNode(tokenList.subList(1, tokenList.size)) //ignore
            TokenType.NUMBER, TokenType.STRING, TokenType.VALUE_IDENTIFIER -> handleLiteral(tokenList)
            TokenType.SEMICOLON -> ASTSingleNode(null, token)
            else -> throw InvalidSyntax("No se qué pasó")
        }
    }

    private fun handleLiteral(tokenList: List<Token>): Node{
        val plusIndex = getPlusIndex(tokenList)
        return if(plusIndex != -1){
            ASTBinaryNode(
                buildBinaryNode(tokenList.subList(plusIndex + 1, tokenList.size)),
                buildBinaryNode(tokenList.subList(0, plusIndex)),
                tokenList[plusIndex]
            )
        }else{
            if(tokenList.size == 1){
                ASTSingleNode(null, tokenList.first())
            }else {
                ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), tokenList.first())
            }
        }
    }

    private fun getPlusIndex(tokenList: List<Token>): Int{
        var index = 0
        for(token in tokenList){
            when(token.type){
                TokenType.PLUS -> return index
                else -> index += 1
            }
        }
        return -1
    }

}