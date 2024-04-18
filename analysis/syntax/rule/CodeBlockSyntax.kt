package parser.analysis.syntax.rule

import parser.analysis.syntax.rule.common.HasValidSeparators
import token.Token
import token.TokenType

class CodeBlockSyntax(
    private val delimiterToken: TokenType,
) : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return hasValidSeparatorsInContent(tokenList)
    }

    private fun hasValidSeparatorsInContent(content: List<Token>): Boolean {
        return when {
            content.isNotEmpty() -> {
                hasValidSeparator(content, TokenType.LEFTPAREN, TokenType.RIGHTPAREN) &&
                    hasValidSeparator(content, TokenType.LEFTBRACE, TokenType.RIGHTBRACE)
            }
            else -> true
        }
    }

    private fun hasValidSeparator(
        content: List<Token>,
        left: TokenType,
        right: TokenType,
    ): Boolean {
        return HasValidSeparators(
            left,
            right,
        ).checkSyntax(content)
    }
}
