package org.example.parser.sintactic.print

import org.example.parser.sintactic.SintacticChecker
import org.example.token.Token
import org.example.token.TokenType

class hasCombination:SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return if(hasEnoughLength(tokenList)){
            if(justHaveNumber().checkSyntax(tokenList)){
                checkNumberContent(getContent(tokenList))
            }else {
                checkContent(getContent(tokenList)) // a + b
            }
        }else false
    }

    private fun checkContent(tokenList: List<Token>): Boolean{
        var index = 0
        while(index < tokenList.size){
            when(tokenList[index].type){
                TokenType.STRING, TokenType.NUMBER, TokenType.VALUE_IDENTIFIER ->{ index += 1 }
                TokenType.PLUS -> if(isNextLiteral(tokenList, index)) index+=1 else return false
                else -> return false
            }
        }
        return true
    }

    private fun checkNumberContent(tokenList: List<Token>): Boolean{
        var index = 0
        while(index < tokenList.size){
            when(tokenList[index].type){
                TokenType.NUMBER, TokenType.VALUE_IDENTIFIER -> index += 1
                TokenType.PLUS, TokenType.STAR, TokenType.SLASH, TokenType.MINUS -> {
                    if(isNextLiteral(tokenList, index)) index+=1 else return false
                }
                else -> return false
            }
        }
        return true
    }

    private fun isNextLiteral(tokenList: List<Token>, index: Int): Boolean{
        return if(index < tokenList.size-1){
            when(tokenList[index + 1].type){
                TokenType.STRING, TokenType.NUMBER, TokenType.VALUE_IDENTIFIER -> true
                else -> false
            }
        }else{
            return false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size > 5
    }

    private fun getContent(tokenList: List<Token>): List<Token>{
        return tokenList.subList(2, tokenList.size - 2)
    }
}