package org.example.parser

import org.example.token.Token
import org.example.token.TypeEnum
class DeclarationParse(){

    fun parse(tokenList: List<Token>): Node{ //Y si es una operación aritmetica?
        return recursiveParse(tokenList)
    }

    private fun recursiveParse(tokenList: List<Token>): Node{
        val token: Token = tokenList.first()
        when(token.type){
            TypeEnum.SEMICOLON -> {  return ASTSingleNode(null, tokenList.first()) }
            TypeEnum.NUMBER, TypeEnum.STRING -> {
                if(!isLeaf(tokenSubList(tokenList))){ return binaryOperation(tokenList) }
                else{ return ASTSingleNode(recursiveParse(tokenSubList(tokenList)), tokenList.first())}
            }
            else -> {  return ASTSingleNode(recursiveParse(tokenSubList(tokenList)), tokenList.first()) }
        }
    }

    private fun isLeaf(tokenList: List<Token>): Boolean{
        when(tokenList.first().type){
            TypeEnum.PLUS, TypeEnum.MINUS, TypeEnum.STAR, TypeEnum.SLASH -> { return false }
            else -> { return true }
        }
    }

    private fun binaryOperation(tokenList: List<Token>): Node{
        val token: Token = tokenList.first()
        when(token.type){
            TypeEnum.STRING, TypeEnum.NUMBER -> { //Possible leaf?
                if(!isLeaf(tokenSubList(tokenList))){ return consturctBinaryNode(tokenList) } //there is another one
                else { return ASTSingleNode(null, token)} // end
            } //it is a literal
            else -> { return ASTSingleNode(null, token)} //should be SemiColon
        }
    }

    private fun consturctBinaryNode(tokenList: List<Token>): Node{
        val plusIndex = getAritmeticOperatorIndex(tokenList, TypeEnum.PLUS)
        if(plusIndex != -1){ //there is a Plus symbol so split operations
            return ASTBinaryNode(
                binaryOperation(tokenList.subList(plusIndex + 1, tokenList.size)),
                binaryOperation(tokenList.subList(0, plusIndex)),
                tokenList.get(plusIndex)
            )
        }
        else{//if the operator is not plus, does not matter the order
            return ASTBinaryNode(
                binaryOperation(tokenList.subList(0,1)),
                binaryOperation(tokenList.subList(2, tokenList.size)),
                tokenList.get(2)
            )
        }
    }

    private fun tokenSubList(tokenList: List<Token>): List<Token>{
        return tokenList.subList(1, tokenList.size)
    }

    private fun getAritmeticOperatorIndex(tokenList: List<Token>, type: TypeEnum): Int{
        var position = 0
        for (token in tokenList){
            when(token.type){
                type -> return position
                else -> {
                    position +=1
                    continue
                }
            }
        }
        return -1
    }
}