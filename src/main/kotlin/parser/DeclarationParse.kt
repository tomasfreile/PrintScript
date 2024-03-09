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
            TypeEnum.NUMBER, TypeEnum.STRING -> { return handleLiteral(tokenList) }
            else -> {  return ASTSingleNode(recursiveParse(tokenSubList(tokenList)), tokenList.first()) }
        }
    }

    private fun handleLiteral(tokenList: List<Token>): Node{
        return if(hasBinaryOperation(tokenSubList(tokenList))){ //there is a Literal, might be a binary operation
            hanldeBinaryOperation(tokenList) //it is binary operation, so let's do it ;)
        } else{ // it is not binary, so keep going until semiColon
            ASTSingleNode(recursiveParse(tokenSubList(tokenList)), tokenList.first())
        }
    }

    private fun hasBinaryOperation(tokenList: List<Token>): Boolean{
        when(tokenList.first().type){ //checks if should stop
            TypeEnum.SEMICOLON -> { return false } //if next is semicolon, stop
            TypeEnum.PLUS, TypeEnum.MINUS, TypeEnum.STAR, TypeEnum.SLASH -> { return true } // at least there is a binary Operation
            else -> { return false } // smth different? keep it open for update
        }
    }

    private fun hanldeBinaryOperation(tokenList: List<Token>): Node{
        val token: Token = tokenList.first()
        when(token.type){
            TypeEnum.STRING, TypeEnum.NUMBER -> { //Is a Literal
                return if(hasBinaryOperation(tokenSubList(tokenList))){ // maybe there is another binary opeartion
                    bakeBinaryNode(tokenList)  //bake a binary the node!!!
                } else { //is just a literal and next should be a SemiColon -> " ; "
                    recursiveParse(tokenList) //start again, next should just be a Semicolon -> " ; ", keep it as possible update
                } // end
            }
            else -> { return ASTSingleNode(null, token)} //should be SemiColon. Not sure if is it reachable, keep it as possible update
        }
    }

    private fun bakeBinaryNode(tokenList: List<Token>): Node{
        return if (isSplit(tokenList)) { //If there is a Plus or Minus, should split the terms
            split(tokenList)
        } else{ //if the operator is not plus, does not matter the order
            ASTBinaryNode(
                recursiveParse(tokenList.subList(2, tokenList.size)), //
                recursiveParse(tokenList.subList(0,1)),
                tokenList.get(1) //Papa node
            )
        }
    }

    private fun isSplit(tokenList: List<Token>): Boolean{
        val plusIndex = getAritmeticOperatorIndex(tokenList, TypeEnum.PLUS)
        val minusIndex = getAritmeticOperatorIndex(tokenList, TypeEnum.MINUS)
        return plusIndex > 0 || minusIndex > 0 //verify if there is a sign for split
    }

    private fun split(tokenList: List<Token>): Node{ // I should check which comes first, Minus or Plus
        val plusIndex = getAritmeticOperatorIndex(tokenList, TypeEnum.PLUS)
        val minusIndex = getAritmeticOperatorIndex(tokenList, TypeEnum.MINUS)
        return if(plusIndex < minusIndex){ // if plus index comes first
                constructSplitNode(tokenList, plusIndex)
        } else{                            // else minus index comes first
            constructSplitNode(tokenList, minusIndex)
        }
    }

    private fun constructSplitNode(tokenList: List<Token>, splitIndex: Int): Node{
        return ASTBinaryNode(
            recursiveParse(tokenList.subList(splitIndex + 1, tokenList.size)), //start new parse with the partition
            recursiveParse(tokenList.subList(0, splitIndex)), //start new parse with the partition
            tokenList.get(splitIndex) //Papa node
        )
    }

    private fun tokenSubList(tokenList: List<Token>): List<Token>{
        return tokenList.subList(1, tokenList.size)
    }

    private fun getAritmeticOperatorIndex(tokenList: List<Token>, type: TypeEnum): Int{ //gets first found
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