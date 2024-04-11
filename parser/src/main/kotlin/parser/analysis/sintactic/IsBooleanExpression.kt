package parser.analysis.sintactic

import token.Token
import token.TokenType

/*
    By the moment Boolean expression has no operators
 */
class IsBooleanExpression : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            when {
                isLiteral(token) || ignore(token) -> continue
                else -> return false
            }
        }
        return true
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.BOOLEAN_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }

    private fun ignore(token: Token): Boolean {
        return when (token.type) {
            TokenType.SEMICOLON -> true
            else -> false
        }
    }
}
