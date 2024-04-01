package parser.semantic

import parser.sintactic.IsDeclarative
import token.Token
import token.TokenType

class StringTypeAssignationContent : SemanticChecker {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        if (IsDeclarative().checkSyntax(tokenList)) {
            for (token in tokenList.subList(5, tokenList.size - 1)) {
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
            TokenType.STRING, TokenType.VALUE_IDENTIFIER -> true
            else -> false
        }
    }

    private fun isValidOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS -> true
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
