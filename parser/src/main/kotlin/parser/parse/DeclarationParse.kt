package parser.parse

import org.example.parser.semantic.NumberTypeDeclaration
import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import token.Token
import token.TokenType

data class InvalidSemanticError(override val message: String): Exception(message)
data class InvalidSyntaxError(override val message: String): Exception(message)

class DeclarationParse(): Parse {

    override fun parse(tokenList: List<Token>): Node { //Y si es una operación aritmetica?
        return recursiveParse(tokenList)
    }

    private fun recursiveParse(tokenList: List<Token>): Node{
        val token: Token = tokenList.first()
        return when(token.type){
            TokenType.SEMICOLON -> {  ASTSingleNode(null, tokenList.first()) } //break recursion because of semicolon
            TokenType.LEFT_PAREN -> { handleLeftParen(tokenList) } //solve inner problem
            TokenType.RIGHT_PAREN -> { parse(tokenSubList(tokenList)) } //ignore right paren, just for logic problems
            TokenType.NUMBER, TokenType.STRING -> { handleLiteral(tokenList) }
            else -> { ASTSingleNode(parse(tokenSubList(tokenList)), tokenList.first()) }
        }
    }

    private fun handleNumberTypeSemantic(tokenList: List<Token>): Node{ //first is Number_Type
        return if(NumberTypeDeclaration().checkSemantic(tokenList)){
            ASTSingleNode(recursiveParse(tokenList.subList(1, tokenList.size)), tokenList.first())
        }else{
            throw InvalidSemanticError("Invalid semantic for NumberType")
        }
    }

    private fun handleLeftParen(tokenList: List<Token>): Node{
        val rightParenIndex = getArithmeticOperatorIndex(tokenList, TokenType.RIGHT_PAREN)
        return if(hasBinaryOperation(tokenList.subList(rightParenIndex + 1, tokenList.size))){ //maybe is (3 + 5) * 3
            ASTBinaryNode(
                parse(tokenList.subList(rightParenIndex + 2, tokenList.size)), // 3 the operation
                parse(tokenList.subList(1, rightParenIndex)), //  3 + 5 the operation
                tokenList.get(rightParenIndex + 1) // * the operator
            )
        }else{
            parse(tokenList.subList(1, tokenList.size))   // No contempla el SEMICOLON?? Cómo los saco? Y si elimino el semicolon de la lista de tokens?? mas simple??
        } // Parse what is inside the parenthesis
    }

    private fun handleLiteral(tokenList: List<Token>): Node{
        return if(hasBinaryOperation(tokenSubList(tokenList))){ //there is a Literal, might be a binary operation
            hanldeBinaryOperation(tokenList) //it is binary operation, so let's do it ;)
        } else{ // it is not binary, so keep going until semiColon or break recursion
            if( isBreakRecursion(tokenList) ){
                ASTSingleNode(null, tokenList.first()) //break recursion because of literal on right side!! open for update
            } else {
                ASTSingleNode(parse(tokenSubList(tokenList)), tokenList.first())
            }
        }
    }

    private fun isBreakRecursion(tokenList: List<Token>): Boolean {
        return tokenList.size == 1
    }

    private fun hasBinaryOperation(tokenList: List<Token>): Boolean{
        if(tokenList.isEmpty()){ return false }
        return when(tokenList.first().type){ //checks if should stop
            TokenType.SEMICOLON -> { false } //if next is semicolon, stop
            TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH -> { true } // at least there is a binary Operation
            else -> { return false } // smth different? keep it open for update
        }
    }

    private fun hanldeBinaryOperation(tokenList: List<Token>): Node{
        val token: Token = tokenList.first()
        return when(token.type){
            TokenType.STRING, TokenType.NUMBER -> { //Is a Literal
                if(hasBinaryOperation(tokenSubList(tokenList))){ // maybe there is another binary opeartion
                    bakeBinaryNode(tokenList)  //bake a binary the node!!!
                } else { //is just a literal and next should be a SemiColon -> " ; "
                    parse(tokenList) //start again, next should just be a Semicolon -> " ; ", keep it as possible update
                } // end
            }
            else -> { ASTSingleNode(null, token)} //should be SemiColon. Not sure if is it reachable, keep it as possible update
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
        val plusIndex = getArithmeticOperatorIndex(tokenList, TokenType.PLUS)
        val minusIndex = getArithmeticOperatorIndex(tokenList, TokenType.MINUS)
        return plusIndex > 0 || minusIndex > 0 //verify if there is a sign for split
    }

    private fun split(tokenList: List<Token>): Node{ // I should check which comes first, Minus or Plus, at least one of each > 0 because of split condition
        val plusIndex = getArithmeticOperatorIndex(tokenList, TokenType.PLUS)
        val minusIndex = getArithmeticOperatorIndex(tokenList, TokenType.MINUS)
        return when{
            plusIndex > 0 && minusIndex > 0 -> { splitWhenBothIndexesPositive(tokenList, minusIndex, plusIndex) }
            plusIndex > 0 -> { constructSplitNode(tokenList, plusIndex) }
            else -> { constructSplitNode(tokenList, minusIndex) } //minusIndex > 0
        }
    }

    private fun splitWhenBothIndexesPositive(tokenList: List<Token>, minusIndex: Int, plusIndex: Int): Node{
        return if(plusIndex > minusIndex){
            constructSplitNode(tokenList, minusIndex)
        } else{
            constructSplitNode(tokenList, plusIndex)
        }
    }

    private fun constructSplitNode(tokenList: List<Token>, splitIndex: Int): Node{
        return ASTBinaryNode(
            parse(tokenList.subList(splitIndex + 1, tokenList.size)), //start new parse with the partition
            parse(tokenList.subList(0, splitIndex)), //start new parse with the partition
            tokenList.get(splitIndex) //Papa node
        )
    }

    private fun tokenSubList(tokenList: List<Token>): List<Token>{
        return tokenList.subList(1, tokenList.size)
    }

    private fun getArithmeticOperatorIndex(tokenList: List<Token>, type: TokenType): Int{ //gets first found
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