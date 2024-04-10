package parser.analysis.sintactic

import parser.InvalidSyntaxException
import token.Token
import token.TokenType

class IsArithmeticExpression : SyntaxRule {
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
            TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
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
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun skipParenContent(tokenList: List<Token>): List<Token> {
        return when {
            isParenContentValid(tokenList) -> tokenList.subList(getRightParenIndex(tokenList) + 1, tokenList.size)
            else -> throw InvalidSyntaxException("Invalid Syntax Exception on line: " + tokenList.first().start.row)
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
            TokenType.RIGHT_PAREN, TokenType.SEMICOLON -> true
            else -> false
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

    private fun isParenValid(tokenList: List<Token>): Boolean {
        return HasPairOfParen().checkSyntax(tokenList)
    }

    private fun condition(
        tokenList: List<Token>,
        index: Int,
    ): Boolean {
        return index < tokenList.size && tokenList.isNotEmpty()
    }
}
