package parser.nodeBuilder

import ast.AstNode
import ast.BinaryOperationNode
import token.Token
import token.TokenType

class ArithmeticNodeBuilder : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        return when {
            isSplit(tokenList) -> split(tokenList)
            isLiteral(tokenList.first()) -> createLiteralNode(tokenList)
            isOtherOperator(tokenList[1]) -> createBinaryNode(tokenList, 1) // L O L format
            else -> createLiteralNode(tokenList)
        }
    }

    private fun createBinaryNode(
        tokenList: List<Token>,
        opIndex: Int,
    ): AstNode {
        return BinaryOperationNode(
            build(tokenList.subList(0, opIndex)),
            build(tokenList.subList(opIndex + 1, tokenList.size)),
            tokenList[opIndex].type,
        )
    }

    private fun createLiteralNode(tokenList: List<Token>): AstNode {
        return LiteralNodeBuilder().build(tokenList)
    }

    private fun isSplit(tokenList: List<Token>): Boolean {
        val plusIndex = getOperatorIndex(tokenList, TokenType.PLUS)
        val minusIndex = getOperatorIndex(tokenList, TokenType.MINUS)
        return plusIndex > 0 || minusIndex > 0
    }

    private fun split(tokenList: List<Token>): AstNode {
        val plusIndex = getOperatorIndex(tokenList, TokenType.PLUS)
        val minusIndex = getOperatorIndex(tokenList, TokenType.MINUS)
        return if (isFirst(minusIndex, plusIndex)) {
            createBinaryNode(tokenList, minusIndex)
        } else {
            createBinaryNode(tokenList, plusIndex)
        }
    }

    private fun getOperatorIndex(
        tokenList: List<Token>,
        type: TokenType,
    ): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.PLUS, TokenType.MINUS -> return i
                else -> i++
            }
        }
        return -1
    }

    private fun isFirst(
        observedIndex: Int,
        otherIndex: Int,
    ): Boolean {
        return observedIndex > 0 && (otherIndex < 0 || observedIndex < otherIndex)
    }

    private fun isOtherOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.STAR, TokenType.SLASH -> true
            else -> false
        }
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }
}
