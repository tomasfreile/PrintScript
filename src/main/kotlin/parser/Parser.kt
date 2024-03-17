package org.example.parser

import org.example.token.Token
import org.example.token.TypeEnum

class Parser {
    fun parse(tokenList: List<Token>): Node?{
        if(tokenList.isNotEmpty()){
            val token = tokenList.first()
            when(token.type){
                TypeEnum.VARIABLE_KEYWORD -> return DeclarationParse().parse(tokenList)
                TypeEnum.PRINT -> return PrintParse().parse(tokenList)
                else -> {}
            }
        }
        return null
    }
}