package parser.nodeBuilder

import ast.AstNode
import ast.BinaryOperationNode
import token.Token
import token.TokenType

class StringNodeBuilder : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        return when {
            isBreakRecursion(tokenList) -> buildLiteralNode(tokenList.first())
            else -> buildBinaryOperationNode(tokenList, getSplitIndex(tokenList))
        }
    }

    private fun buildLiteralNode(token: Token): AstNode {
        return LiteralNodeBuilder().build(listOf(token))
    }

    private fun buildBinaryOperationNode(
        tokenList: List<Token>,
        index: Int,
    ): AstNode {
        return BinaryOperationNode(
            build(tokenList.subList(0, index)),
            build(tokenList.subList(index + 1, tokenList.size)),
            tokenList[index].type,
        )
    }

    private fun isBreakRecursion(tokenList: List<Token>): Boolean {
        return tokenList.size == 1 && isLiteral(tokenList.first())
    }

    private fun getSplitIndex(tokenList: List<Token>): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.PLUS, TokenType.MINUS -> return i
                else -> i += 1
            }
        }
        return -1
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBER_LITERAL, TokenType.STRING_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL, TokenType.BOOLEAN_LITERAL -> true
            else -> false
        }
    }
}
