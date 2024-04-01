package parser.analysis.semantic

import parser.analysis.sintactic.IsPrint
import token.Token
import token.TokenType

class PrintContent : SemanticChecker {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        if (IsPrint().checkSyntax(tokenList)) {
            for (token in tokenList.subList(2, tokenList.size - 2)) {
                when {
                    isValidToken(token) -> continue
                    else -> return false
                }
            }
            return true
        }
        return false
    }

    private fun isValidToken(token: Token): Boolean {
        return isValidLiteral(token) ||
            isValidOperator(token) ||
            isParen(token)
    }

    private fun isValidLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING, TokenType.NUMBER, TokenType.VALUE_IDENTIFIER -> true
            else -> false
        }
    }

    private fun isValidOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.MINUS, TokenType.PLUS, TokenType.STAR, TokenType.SLASH -> true
            else -> false
        }
    }

    private fun isParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }
}
