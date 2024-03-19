package org.example.parser

import org.example.token.Token
import org.example.token.TokenType

class Parser {
    fun parse(tokenList: List<Token>): Node?{
        if(!tokenList.isEmpty()){
            val token = tokenList.first()
            when(token.type){
                TokenType.VARIABLE_KEYWORD -> return DeclarationParse().parse(tokenList)
                else -> {}
            }
        }
        return null
    }
}