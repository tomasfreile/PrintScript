package org.example.parser.sintactic.declarative

import org.example.parser.sintactic.SintacticChecker
import token.Token
import token.TokenType

class isDeclarative: SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return if(hasEnoughLength(tokenList)){
            checkStructure(tokenList) == 7 //If is equals to 7, at least has these structure
        }else{
            false
        }
    }

    private fun checkStructure(tokenList: List<Token>): Int{
        var points = 0
        if(tokenList[0].type == TokenType.VARIABLE_KEYWORD) points += 1
        if(tokenList[1].type == TokenType.VALUE_IDENTIFIER) points += 1
        if(tokenList[2].type == TokenType.COLON) points += 1
        if(tokenList[3].type == TokenType.NUMBER_TYPE || tokenList[3].type == TokenType.STRING_TYPE) points += 1
        if(tokenList[4].type == TokenType.ASSIGNATION) points += 1
        if(isLiteral(tokenList[5].type)) points += 1
        if(tokenList[tokenList.size - 1].type == TokenType.SEMICOLON) points += 1
        return points
    }

    private fun isLiteral(type: TokenType): Boolean{
        return when(type){
            TokenType.NUMBER, TokenType.STRING, TokenType.VALUE_IDENTIFIER -> true
            else -> false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size >= 7
    }

}