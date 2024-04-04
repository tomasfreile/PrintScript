package parser.utils

import ast.ASTBinaryNode
import ast.Node
import parser.InvalidSyntax
import parser.parse.Parse
import token.Token
import token.TokenType

class BinaryNodeBuilder(private val parseAlgorithm: Parse) {
    fun build(tokenList: List<Token>): Node {
        return when {
            isOperator(tokenList[1]) -> buildCommonNode(tokenList)
            isLeftParen(tokenList.first()) -> handleLeftParen(tokenList)
            else -> throw InvalidSyntax("Syntax error")
        }
    }

    private fun buildCommonNode(tokenList: List<Token>): Node {
        return if (isSplit(tokenList)) { // If there is a Plus or Minus, should split the terms
            split(tokenList)
        } else { // if the operator is not plus, does not matter the order, could be STAR or SLASH
            ASTBinaryNode(
                parseAlgorithm.parse(tokenList.subList(indexIsParen(tokenList), tokenList.size)),
                parseAlgorithm.parse(tokenList.subList(0, 1)),
                tokenList[1],
            )
        }
    }

    private fun indexIsParen(tokenList: List<Token>): Int {
        return when (tokenList[2].type) {
            TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN -> 3
            else -> 2
        }
    }

    private fun handleLeftParen(tokenList: List<Token>): Node {
        val index = getRightIndexParen(tokenList)
        if (index + 1 >= tokenList.size) throw InvalidSyntax("Invalid syntax") // it always ends with Semicolon
        return if (index + 2 == tokenList.size && tokenList[tokenList.size - 1].type == TokenType.SEMICOLON ||
            isPrintCase(
                tokenList,
                index,
            )
        ) {
            parseAlgorithm.parse(tokenList.subList(1, tokenList.size))
        } else {
            ASTBinaryNode(
                parseAlgorithm.parse(tokenList.subList(index + 2, tokenList.size)),
                buildCommonNode(tokenList.subList(1, index)),
                tokenList[index + 1],
            )
        }
    }

    private fun isPrintCase(
        tokenList: List<Token>,
        index: Int,
    ): Boolean {
        return tokenList[index + 1].type == TokenType.RIGHT_PAREN &&
            tokenList[index + 2].type == TokenType.SEMICOLON
    }

    private fun getRightIndexParen(tokenList: List<Token>): Int {
        var index = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.RIGHT_PAREN -> break
                else -> index += 1
            }
        }
        return index
    }

    private fun isSplit(tokenList: List<Token>): Boolean {
        val plusIndex = getArithmeticOperatorIndex(tokenList, TokenType.PLUS)
        val minusIndex = getArithmeticOperatorIndex(tokenList, TokenType.MINUS)
        return plusIndex > 0 || minusIndex > 0 // verify if there is a sign for split
    }

    private fun split(tokenList: List<Token>): Node { // I should check which comes first, Minus or Plus, at least one of each > 0
        val plusIndex = getArithmeticOperatorIndex(tokenList, TokenType.PLUS)
        val minusIndex = getArithmeticOperatorIndex(tokenList, TokenType.MINUS)
        return when {
            plusIndex > 0 && minusIndex > 0 -> {
                splitWhenBothIndexesPositive(tokenList, minusIndex, plusIndex)
            }
            plusIndex > 0 -> {
                constructSplitNode(tokenList, plusIndex)
            }
            else -> {
                constructSplitNode(tokenList, minusIndex)
            } // minusIndex > 0
        }
    }

    private fun splitWhenBothIndexesPositive(
        tokenList: List<Token>,
        minusIndex: Int,
        plusIndex: Int,
    ): Node {
        return if (plusIndex > minusIndex) {
            constructSplitNode(tokenList, minusIndex)
        } else {
            constructSplitNode(tokenList, plusIndex)
        }
    }

    private fun constructSplitNode(
        tokenList: List<Token>,
        splitIndex: Int,
    ): Node {
        return ASTBinaryNode(
            parseAlgorithm.parse(tokenList.subList(splitIndex + 1, tokenList.size)),
            parseAlgorithm.parse(tokenList.subList(0, splitIndex)),
            tokenList[splitIndex],
        )
    }

    private fun getArithmeticOperatorIndex(
        tokenList: List<Token>,
        type: TokenType,
    ): Int { // gets first found
        var position = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.LEFT_PAREN -> break // case of 3 * ( 2 + 2 )
                type -> return position
                else -> {
                    position += 1
                    continue
                }
            }
        }
        return -1
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.MINUS, TokenType.SLASH, TokenType.STAR -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }
}
