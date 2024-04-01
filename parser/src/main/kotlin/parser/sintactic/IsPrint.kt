package parser.sintactic

import token.Token
import token.TokenType

class IsPrint : SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return if (hasEnoughLength(tokenList)) {
            checkStructure(tokenList)
        } else {
            false
        }
    }

    private fun checkStructure(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.PRINT) points += 1
        if (tokenList[1].type == TokenType.LEFT_PAREN) points += 1
        if (isLiteral(tokenList[2].type)) points += 1
        if (tokenList[tokenList.size - 2].type == TokenType.RIGHT_PAREN) points += 1
        if (tokenList[tokenList.size - 1].type == TokenType.SEMICOLON) points += 1
        return points == 5
    }

    private fun isLiteral(type: TokenType): Boolean {
        return when (type) {
            TokenType.NUMBER, TokenType.STRING, TokenType.VALUE_IDENTIFIER, TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size >= 5
    }
}
