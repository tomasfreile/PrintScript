package org.example.parser.sintactic.declarative

import org.example.parser.sintactic.SintacticChecker
import org.example.token.Token
import org.example.token.TypeEnum

class hasNoneConsecutiveValue: SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return if(hasEnoughLength(tokenList)){
            checkValues(tokenList, findType(tokenList))
        }else{
            false
        }
    }

    private fun checkValues(tokenList: List<Token>, type: TypeEnum): Boolean{
        var previus = tokenList[5]
        var next = tokenList[7]
        var index = 6
        while(next.type != TypeEnum.SEMICOLON){
            if(previus.type == type && tokenList[index].type == type) return false //let a:Int = 3 3;
            else{
                previus = tokenList[index]
                index += 1
                next = tokenList[index + 1]
            }
        }
        return true
    }

    private fun findType(tokenList: List<Token>): TypeEnum{
        return tokenList[5].type
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size >= 7
    }

}