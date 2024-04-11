package parser.analysis.syntax.expression

import parser.InvalidSyntaxException
import parser.analysis.syntax.rule.HasPairOfParen
import token.Token
import token.TokenType

class IsArithmeticExpression : Expression {
    override fun isExpression(expression: List<Token>): Boolean {
        var index = 0
        var tokenCopy = expression
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
            else -> throw InvalidSyntaxException(
                "Invalid data type on coord: ( " + tokenList.first().start.row + "; " + tokenList.first().start.column + ")",
            )
        }
    }

    private fun isParenContentValid(tokenList: List<Token>): Boolean {
        return if (isParenValid(tokenList)) {
            val parenContent = tokenList.subList(1, getRightParenIndex(tokenList))
            isExpression(parenContent)
        } else {
            false
        }
    }

    private fun ignore(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHT_PAREN -> true
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
