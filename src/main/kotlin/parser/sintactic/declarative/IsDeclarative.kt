package org.example.parser.sintactic.declarative

import org.example.parser.sintactic.SintacticChecker
import org.example.token.Token
import org.example.token.TokenType

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
        if(tokenList.get(0).type == TokenType.VARIABLE_KEYWORD) points += 1
        if(tokenList.get(1).type == TokenType.VALUE_IDENTIFIER) points += 1
        if(tokenList.get(2).type == TokenType.COLON) points += 1
        if((tokenList.get(3).type == TokenType.NUMBER_TYPE) || (tokenList.get(3).type == TokenType.STRING_TYPE)) points += 1
        if(tokenList.get(4).type == TokenType.ASSIGNATION) points += 1
        if((tokenList.get(5).type == TokenType.STRING) || (tokenList.get(5).type == TokenType.NUMBER)) points += 1
        if(tokenList.get(tokenList.size - 1).type == TokenType.SEMICOLON) points += 1
        return points
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size >= 7
    }

}