package parser.analysis.syntax.common

import parser.analysis.syntax.SyntaxRule
import token.Token
import token.TokenType

class HasSentenceSeparator(private val separator: TokenType) : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return isValidKey(tokenList[tokenList.size - 1])
    }

    private fun isValidKey(token: Token): Boolean {
        return when (token.type) {
            separator -> true
            else -> false
        }
    }
}
