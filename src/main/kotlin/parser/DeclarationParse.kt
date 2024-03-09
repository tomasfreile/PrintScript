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
                if(thereIsOtherOperation(tokenSubList(tokenList))){ return binaryOperation(tokenList) }
                else{ return ASTSingleNode(recursiveParse(tokenSubList(tokenList)), tokenList.first())}
            }
            else -> {  return ASTSingleNode(recursiveParse(tokenSubList(tokenList)), tokenList.first()) }
        }
    }

    private fun thereIsOtherOperation(tokenList: List<Token>): Boolean{
        when(tokenList.first().type){
            TypeEnum.PLUS, TypeEnum.MINUS, TypeEnum.STAR, TypeEnum.SLASH -> { return true }
            else -> { return false }
        }
    }

    private fun binaryOperation(tokenList: List<Token>): Node{
        val token: Token = tokenList.first()
        when(token.type){
            TypeEnum.STRING, TypeEnum.NUMBER -> {
                if(thereIsOtherOperation(tokenSubList(tokenList))){ return consturctBinaryNode(tokenList) } //there is another one
                else { return ASTSingleNode(null, token)} // end
            } //it is a literal
            TypeEnum.PLUS -> { return ASTBinaryNode(null, null, token)} //NOT DONEE
            else -> { return ASTSingleNode(null, token)} //NOT DONEEE
        }
    }

    private fun consturctBinaryNode(tokenList: List<Token>): Node{
        val plusIndex = plusFinder(tokenList)
        if(plusIndex != -1){ //there is a Plus symbol so split operations
            return ASTBinaryNode(
                binaryOperation(tokenList.subList(0, plusIndex)),
                binaryOperation(tokenList.subList(plusIndex + 1, tokenList.size)),
                tokenList.get(plusIndex)
            )
        }
        else{
            return ASTSingleNode(null, tokenList.first()) //NOT DONEEE!!!
        }
    }

    private fun tokenSubList(tokenList: List<Token>): List<Token>{
        return tokenList.subList(1, tokenList.size)
    }

    private fun plusFinder(tokenList: List<Token>): Int {
        var position = 0
        for (token in tokenList){
            when(token.type){
                TypeEnum.PLUS -> return position
                else -> {
                    position += 1
                    continue
                }
            }
        }
        return -1
    }
}