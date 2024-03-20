package parser.sintactic.commons

import org.example.parser.sintactic.SintacticChecker
import org.example.parser.sintactic.assignation.isAssignation
import org.example.parser.sintactic.declarative.isDeclarative
import org.example.parser.sintactic.print.isPrint
import parser.Parse
import parser.ParseType
import token.Token
import token.TokenType

class hasCombination:SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean { //If length is not enough, it will be NIL
        var type = checkParseType(tokenList)
        return when(type){
            ParseType.ASSIGNATION -> checkStructure(tokenList, 2, tokenList.size - 1)
            ParseType.PRINT -> checkStructure(tokenList, 2, tokenList.size - 2)
            ParseType.DECLARATION -> checkStructure(tokenList, 5, tokenList.size - 1)
            ParseType.NIL -> false
        }
    }

    private fun checkStructure(tokenList: List<Token>, from: Int, to: Int): Boolean{
        return if(justHaveNumber().checkSyntax(tokenList)){
            checkNumberContent(tokenList.subList(from, to))
        }else{
            checkContent(tokenList.subList(from, to))
        }
    }

    private fun checkContent(tokenList: List<Token>): Boolean{
        if(tokenList.size == 1) return false //is just a simple expression, no combination!!!
        var index = 0
        while(index < tokenList.size){
            when(tokenList[index].type){
                TokenType.STRING, TokenType.NUMBER, TokenType.VALUE_IDENTIFIER ->{ index += 1 }
                TokenType.PLUS -> if(isNextLiteral(tokenList, index)) index += 1 else return false
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

    private fun checkParseType(tokenList: List<Token>): ParseType{
        return when{
            isDeclarative().checkSyntax(tokenList) -> ParseType.DECLARATION
            isPrint().checkSyntax(tokenList) -> ParseType.PRINT
            isAssignation().checkSyntax(tokenList) -> ParseType.ASSIGNATION
            else -> ParseType.NIL
        }
    }
}