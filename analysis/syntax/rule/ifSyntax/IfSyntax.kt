package parser.analysis.syntax.rule.ifSyntax

import parser.analysis.syntax.rule.SyntaxRule
import token.Token
import token.TokenType

class IfSyntax : SyntaxRule {
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
        if (tokenList[1].type == TokenType.LEFTPAREN) points += 1
        if (isCondition(tokenList[2])) points += 1
        if (tokenList[3].type == TokenType.RIGHTPAREN) points += 1
        if (tokenList[4].type == TokenType.LEFTBRACE) points += 1
        if (hasContent(tokenList)) points += 1
        if (tokenList[tokenList.size - 1].type == TokenType.RIGHTBRACE) points += 1
        return points == 7
    }

    private fun isCondition(token: Token): Boolean {
        return when (token.type) {
            TokenType.BOOLEANLITERAL, TokenType.VALUEIDENTIFIERLITERAL -> true
            else -> false
        }
    }

    private fun hasContent(tokenList: List<Token>): Boolean {
        val content = tokenList.subList(5, tokenList.size - 1)
        return content.isNotEmpty()
    }
}
