package parser.analysis.sintactic

import token.Token
import token.TokenType

class IsArithmeticExpression : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        var index = 0
        for (token in tokenList) {
            index +=
                when {
                    isParen(token) -> if (isParenValid(tokenList, index)) 1 else return false
                    isLiteral(token) || isOperator(token) || ignore(token) -> 1
                    else -> return false
                }
        }
        return true
    }

    private fun isParenValid(
        tokenList: List<Token>,
        from: Int,
    ): Boolean {
        return HasPairOfParen().checkSyntax(tokenList.subList(from, tokenList.size))
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

    private fun ignore(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }
}
