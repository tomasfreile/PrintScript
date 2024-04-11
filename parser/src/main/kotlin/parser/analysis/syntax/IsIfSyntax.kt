package parser.analysis.syntax

import token.Token
import token.TokenType

class IsIfSyntax : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return when {
            hasEnoughLength(tokenList) -> checkStructure(tokenList)
            else -> false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size >= 7
    }

    private fun checkStructure(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.IF) points += 1
        if (isParen(tokenList[1]) && isParen(tokenList[3])) points += 1
        if (isCondition(tokenList[2])) points += 1
        if (isBrace(tokenList[4]) && isBrace(tokenList[tokenList.size - 1])) points += 1
        return points == 4
    }

    private fun isParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }

    private fun isBrace(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_BRACE, TokenType.RIGHT_BRACE -> true
            else -> false
        }
    }

    private fun isCondition(token: Token): Boolean {
        return when (token.type) {
            TokenType.BOOLEAN_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }
}
