package org.example.parser.sintactic.declarative

import org.example.parser.sintactic.SintacticChecker
import org.example.token.Token
import org.example.token.TokenType

class hasOperatorOnProperWay: SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return if(hasEnoughLength(tokenList)){
            checkPlusOperator(tokenList, findType(tokenList))
        }else{
            false
        }
    }

    private fun checkPlusOperator(tokenList: List<Token>, type: TokenType): Boolean{
        var previus = tokenList[5]
        var next = tokenList[7]
        if(isOperator(previus) || isOperator(next)) return false
        var index = 6
        while(next.type != TokenType.SEMICOLON){
            if(previus.type == type && next.type == type){
                if(!isOperator(tokenList[index])) return false
                else{
                    previus = tokenList[index]
                    index += 1
                    next = tokenList[index + 1]
                }
            }else return false
        }
        return true
    }

    private fun isOperator(token: Token): Boolean{
        return when(token.type){
            TokenType.PLUS, TokenType.STAR, TokenType.SLASH, TokenType.MINUS -> true
            else -> false
        }
    }

    private fun findType(tokenList: List<Token>): TokenType{
        return tokenList[5].type
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size >= 7
    }
}