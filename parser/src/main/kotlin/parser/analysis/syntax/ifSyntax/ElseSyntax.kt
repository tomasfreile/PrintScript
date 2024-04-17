package parser.analysis.syntax.ifSyntax

import parser.analysis.syntax.SyntaxRule
import token.Token
import token.TokenType

class ElseSyntax : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return when {
            hasEnoughLength(tokenList) -> checkStructure(tokenList)
            else -> false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size >= 4
    }

    private fun checkStructure(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.ELSE) points += 1
        if (tokenList[1].type == TokenType.LEFTBRACE) points += 1
        if (hasContent(tokenList)) points += 1
        if (tokenList[tokenList.size - 1].type == TokenType.RIGHTBRACE) points += 1
        return points == 4
    }

    private fun hasContent(tokenList: List<Token>): Boolean {
        val content = tokenList.subList(2, tokenList.size - 1)
        return content.isNotEmpty()
    }
}
