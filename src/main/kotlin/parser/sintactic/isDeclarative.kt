package org.example.parser.sintactic

import org.example.token.Token
import org.example.token.TypeEnum

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
        if(tokenList.get(0).type == TypeEnum.VARIABLE_KEYWORD) points += 1
        if(tokenList.get(1).type == TypeEnum.VALUE_IDENTIFIER) points += 1
        if(tokenList.get(2).type == TypeEnum.COLON) points += 1
        if((tokenList.get(3).type == TypeEnum.NUMBER_TYPE) || (tokenList.get(3).type == TypeEnum.STRING_TYPE)) points += 1
        if(tokenList.get(4).type == TypeEnum.ASSIGNATION) points += 1
        if((tokenList.get(5).type == TypeEnum.STRING) || (tokenList.get(5).type == TypeEnum.NUMBER)) points += 1
        if(tokenList.get(tokenList.size - 1).type == TypeEnum.SEMICOLON) points += 1
        return points
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size >= 7
    }

}