package org.example.parser.sintactic

import org.example.token.Token
import org.example.token.TypeEnum

class isStringOperation: SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return if(hasEnoughLength(tokenList)){
            checkStringConcatenation(tokenList)
        }else{
            false
        }
    }

    private fun checkStringConcatenation(tokenList: List<Token>): Boolean{
        var points = 0
        if(hasValidType(tokenList)) points += 1
        if(hasPlusOnProperWay(tokenList)) points += 1
        return points == 2
    }

    private fun hasValidType(tokenList: List<Token>): Boolean{
        for(token in tokenList.subList(5, tokenList.size-1)) {
            when(token.type){
                TypeEnum.STRING, TypeEnum.PLUS -> continue
                else -> { return false }
            }
        }
        return true
    }

    private fun hasPlusOnProperWay(tokenList: List<Token>): Boolean{
        var previus = tokenList[5]
        var next = tokenList[7]
        var index = 6
        while(next.type != TypeEnum.SEMICOLON){
            if(previus.type == TypeEnum.STRING && next.type == TypeEnum.STRING){
                if(tokenList[index].type != TypeEnum.PLUS){//solo hay un + entre dos strings que se concatenan
                    return false
                }else{
                    previus = tokenList[index]
                    index += 1
                    next = tokenList[index + 1]
                }
            }else{
                return false
            }
        }
        return true
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size >= 7
    }

}