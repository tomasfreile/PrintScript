package parser.analysis.syntax.expression

import token.Token
import token.TokenType

class IsFunctionExpression : Expression {
    override fun isExpression(expression: List<Token>): Boolean {
        return when {
            hasEnoughLength(expression) -> checkStructure(expression)
            else -> false
        }
    }

    private fun checkStructure(expression: List<Token>): Boolean {
        var points = 0
        if (isFunction(expression[0])) points += 1
        if (isLeftParen(expression[1])) points += 1
        if (isRightParen(expression[3])) points += 1
        return points == 3
    }

    private fun isFunction(token: Token): Boolean {
        return when (token.type) {
            TokenType.READ_ENV, TokenType.READ_INPUT, TokenType.WRITE_ENV -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun isRightParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size == 4
    }
}
