package parser.sintactic.commons

import org.example.parser.sintactic.SintacticChecker
import token.Token
import token.TokenType

class justHaveNumber: SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return hasEnoughLength(tokenList) && checkContent(getContent(tokenList))
    }

    private fun checkContent(tokenList: List<Token>): Boolean{
        var index = 0
        while(index < tokenList.size){
            when(tokenList[index].type){
                TokenType.NUMBER, TokenType.VALUE_IDENTIFIER -> index+=1
                TokenType.PLUS, TokenType.MINUS, TokenType.SLASH, TokenType.STAR -> {
                    if(isNextNumber(tokenList, index)) index+=1 else return false
                }
                else -> return false
            }
        }
        return true
    }

    private fun isNextNumber(tokenList: List<Token>, index: Int): Boolean{
        return if(index < tokenList.size-1){
            when(tokenList[index + 1].type){
                TokenType.NUMBER, TokenType.VALUE_IDENTIFIER -> true
                else -> false
            }
        }else{
            return false
        }
    }

    private fun getContent(tokenList: List<Token>): List<Token>{
        return tokenList.subList(2, tokenList.size - 2)
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean{
        return tokenList.size >= 5
    }
}