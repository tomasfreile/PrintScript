package parser.analysis.syntax.expression

import token.Token
import token.TokenType

/*
    By the moment Boolean expression has no operators
 */
class IsBooleanExpression : Expression {
    override fun isExpression(expression: List<Token>): Boolean {
        for (token in expression) {
            when {
                isLiteral(token) -> continue
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
}
