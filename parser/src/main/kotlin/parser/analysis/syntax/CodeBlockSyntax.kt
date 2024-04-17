package parser.analysis.syntax

import parser.analysis.syntax.common.HasValidSeparators
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
                hasValidSeparator(content, TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN) &&
                    hasValidSeparator(content, TokenType.LEFT_BRACE, TokenType.RIGHT_BRACE)
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
