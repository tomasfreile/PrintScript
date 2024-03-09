package org.example.parser

import org.example.token.Token
import org.example.token.TypeEnum
class DeclarationParse(){

    fun parse(tokenList: List<Token>): Node{ //Y si es una operación aritmetica?
        return recursiveParse(tokenList)
    }

    private fun recursiveParse(tokenList: List<Token>): Node{
        val token: Token? = tokenList.first()
        when(token?.type){
            TypeEnum.SEMICOLON -> {  return ASTSingleNode(null, tokenList.first()) }
            else -> {  return ASTSingleNode(recursiveParse(tokenList.subList(1, tokenList.size)), tokenList.first()) }
        }
    }

}