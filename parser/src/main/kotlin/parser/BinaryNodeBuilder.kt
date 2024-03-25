package parser

import ast.ASTBinaryNode
import ast.Node
import parser.parse.Parse
import token.Token
import token.TokenType

class BinaryNodeBuilder(private val parseAlgorithm: Parse) {
    fun build(tokenList: List<Token>): Node {
        return if (isSplit(tokenList)) { // If there is a Plus or Minus, should split the terms
            split(tokenList)
        } else { // if the operator is not plus, does not matter the order, could be STAR or SLASH
            ASTBinaryNode(
                parseAlgorithm.parse(tokenList.subList(2, tokenList.size)),
                parseAlgorithm.parse(tokenList.subList(0, 1)),
                tokenList[1],
            )
        }
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
                type -> return position
                else -> {
                    position += 1
                    continue
                }
            }
        }
        return -1
    }
}
