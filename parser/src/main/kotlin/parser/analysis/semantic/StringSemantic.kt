package parser.analysis.semantic

import parser.analysis.semantic.common.HasValidOperators
import parser.analysis.semantic.common.OperatorIsFormatted
import token.Token
import token.TokenType

class StringSemantic : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        return isFormatted(tokenList) && hasValidOperators(tokenList)
    }

    private fun isFormatted(tokenList: List<Token>): Boolean {
        return OperatorIsFormatted().checkSemantic(tokenList)
    }

    private fun hasValidOperators(tokenList: List<Token>): Boolean {
        return HasValidOperators(getInvalidOperators()).checkSemantic(tokenList)
    }

    private fun getInvalidOperators(): List<TokenType> {
        return listOf(
            TokenType.MINUS,
            TokenType.STAR,
            TokenType.SLASH,
            TokenType.LEFT_PAREN,
            TokenType.RIGHT_PAREN,
            TokenType.LEFT_BRACE,
            TokenType.RIGHT_BRACE,
        )
    }
}
