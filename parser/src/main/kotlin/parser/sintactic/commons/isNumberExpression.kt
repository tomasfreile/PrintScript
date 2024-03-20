package parser.sintactic.commons

import org.example.parser.sintactic.SintacticChecker
import org.example.parser.sintactic.assignation.isAssignation
import org.example.parser.sintactic.declarative.isDeclarative
import org.example.parser.sintactic.print.isPrint
import parser.ParseType
import token.Token
import token.TokenType

class justHaveNumber: SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        var type = checkParseType(tokenList)
        return when(type){
            ParseType.ASSIGNATION -> checkContent(tokenList, 2, tokenList.size - 1) //content position depends on the type of parse
            ParseType.PRINT -> checkContent(tokenList, 2, tokenList.size - 2)
            ParseType.DECLARATION -> checkContent(tokenList, 5, tokenList.size - 1)
            ParseType.NIL -> false
        }
    }

    private fun checkContent(tokenList: List<Token>, from: Int, to: Int): Boolean{
        return isArithmetic(tokenList.subList(from, to))
    }

    private fun isArithmetic(tokenList: List<Token>): Boolean{
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

    private fun checkParseType(tokenList: List<Token>): ParseType {
        return when{
            isDeclarative().checkSyntax(tokenList) -> ParseType.DECLARATION
            isPrint().checkSyntax(tokenList) -> ParseType.PRINT
            isAssignation().checkSyntax(tokenList) -> ParseType.ASSIGNATION
            else -> ParseType.NIL
        }
    }
}