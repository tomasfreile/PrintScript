package parser.analysis.syntax

import parser.InvalidSyntaxException
import parser.analysis.syntax.common.HasPairSeparator
import token.Token
import token.TokenType

class IsArithmeticSyntax : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        var index = 0
        var tokenCopy = tokenList
        while (condition(tokenCopy, index)) {
            val token = tokenCopy[index]
            index +=
                when {
                    isParen(token) -> {
                        tokenCopy = skipParenContent(tokenCopy.subList(index, tokenCopy.size))
                        index = 0
                        continue
                    }
                    isLiteral(token) || isOperator(token) || ignore(token) -> 1
                    else -> return false
                }
        }
        return true
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBERLITERAL, TokenType.VALUEIDENTIFIERLITERAL -> true
            else -> false
        }
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH -> true
            else -> false
        }
    }

    private fun isParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFTPAREN -> true
            else -> false
        }
    }

    private fun skipParenContent(tokenList: List<Token>): List<Token> {
        return when {
            isParenContentValid(tokenList) -> tokenList.subList(getRightParenIndex(tokenList) + 1, tokenList.size)
            else -> throw InvalidSyntaxException(
                "Invalid data type on coord: ( " + tokenList.first().start.row + "; " + tokenList.first().start.column + ")",
            )
        }
    }

    private fun isParenContentValid(tokenList: List<Token>): Boolean {
        return if (isParenValid(tokenList)) {
            val parenContent = tokenList.subList(1, getRightParenIndex(tokenList))
            checkSyntax(parenContent)
        } else {
            false
        }
    }

    private fun ignore(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHTPAREN -> true
            else -> false
        }
    }

    private fun getRightParenIndex(tokenList: List<Token>): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.RIGHTPAREN -> return i
                else -> i += 1
            }
        }
        return -1
    }

    private fun isParenValid(tokenList: List<Token>): Boolean {
        return HasPairSeparator(
            TokenType.LEFTPAREN,
            TokenType.RIGHTPAREN,
        ).checkSyntax(tokenList)
    }

    private fun condition(
        tokenList: List<Token>,
        index: Int,
    ): Boolean {
        return index < tokenList.size && tokenList.isNotEmpty()
    }
}
