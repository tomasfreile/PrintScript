package org.example.parser

import org.example.token.Token
import org.example.token.TypeEnum
class DeclarationParse(){
    private var actual = 0

    fun parse(tokenList: List<Token>): Node{ //Y si es una operación aritmetica?
        return recursiveParse(tokenList)
    }

    private fun recursiveParse(tokenList: List<Token>): Node{
        if(tokenList.size == 1){ //Nodo hoja
            return ASTSingleNode(null, tokenList.first())
        }
        return ASTSingleNode(recursiveParse(tokenList.subList(1, tokenList.size)), tokenList.first())
    }

    private fun getToken(tokenList: List<Token>, expected: TypeEnum? = null): Token?{
        val token = this.peekToken(tokenList)
        if (token != null) { //It has a token
            if(token.type != expected){ //is the one i expected?
                return null
            }
        }
        this.actual = actual + 1 //if is it expected or not, there is a token, so i want it
        return token
    }

    private fun peekToken(tokenList: List<Token>): Token?{
        if(actual < tokenList.size){
            return tokenList.get(actual)
        }
        return null
    }

}