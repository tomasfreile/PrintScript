package parser.nodeBuilder

import ast.AstNode
import ast.BinaryOperationNode
import parser.InvalidSyntaxException
import parser.analysis.sintactic.HasOperationAfterParen
import parser.analysis.sintactic.HasPairOfParen
import token.Token
import token.TokenType

class ArithmeticNodeBuilder : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        return when {
            isSplit(tokenList) -> split(tokenList) // Primero separo en términos
            isLeftParen(tokenList.first()) -> handleLeftParen(tokenList) // Resuelvo paréntesis
            isBinaryOperation(tokenList) -> createBinaryNode(tokenList, 1) // L O L format Resuelvo producto y división
            isLiteral(tokenList.first()) -> createLiteralNode(tokenList) // llegué al final..
            else -> createLiteralNode(tokenList)
        }
    }

    private fun handleLeftParen(tokenList: List<Token>): AstNode {
        val rightParenIndex = getRightParenIndex(tokenList)
        return if (hasOperator(tokenList)) {
            BinaryOperationNode(
                build(tokenList.subList(1, rightParenIndex)),
                build(tokenList.subList(rightParenIndex + 2, tokenList.size)),
                tokenList[rightParenIndex + 1].type,
            )
        } else {
            build(tokenList.subList(1, rightParenIndex))
        }
    }

    private fun hasOperator(tokenList: List<Token>): Boolean {
        return HasOperationAfterParen().checkSyntax(tokenList)
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
        while (i < tokenList.size) {
            when (tokenList[i].type) {
                TokenType.LEFT_PAREN -> i = ignoreParenToIndex(tokenList, i)
                type -> return i
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

    private fun isBinaryOperation(tokenList: List<Token>): Boolean {
        if (tokenList.size == 3) {
            return when (tokenList[1].type) {
                TokenType.STAR, TokenType.SLASH -> isLiteral(tokenList[0]) && isLiteral(tokenList[2])
                else -> false
            }
        }
        return false
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun isParenValid(
        tokenList: List<Token>,
        from: Int,
    ): Boolean {
        return HasPairOfParen().checkSyntax(tokenList.subList(from, tokenList.size))
    }

    private fun ignoreParenToIndex(
        tokenList: List<Token>,
        actual: Int,
    ): Int {
        if (isParenValid(tokenList, actual)) {
            return actual + getOperatorIndex(tokenList.subList(actual + 1, tokenList.size), TokenType.RIGHT_PAREN) + 2
        } else {
            throw InvalidSyntaxException("Invalid Paren Syntax on line: " + tokenList[actual].start.row)
        }
    }

    private fun getRightParenIndex(tokenList: List<Token>): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.RIGHT_PAREN -> return i
                else -> i += 1
            }
        }
        return -1
    }
}
