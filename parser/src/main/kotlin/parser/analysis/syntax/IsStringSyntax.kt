package parser.analysis.syntax

import token.Token
import token.TokenType

class IsStringSyntax : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            when {
                isLiteral(token) || isPlus(token) -> continue
                else -> return false
            }
        }
        return true
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }

    private fun isPlus(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS -> true
            else -> false
        }
    }
}
