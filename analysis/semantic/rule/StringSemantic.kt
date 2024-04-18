package parser.analysis.semantic.rule

import parser.analysis.semantic.rule.common.HasValidValues
import parser.analysis.semantic.rule.common.OperatorIsFormatted
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
        return HasValidValues(getInvalidOperators()).checkSemantic(tokenList)
    }

    private fun getInvalidOperators(): List<TokenType> {
        return listOf(
            TokenType.MINUS,
            TokenType.STAR,
            TokenType.SLASH,
            TokenType.LEFTPAREN,
            TokenType.RIGHTPAREN,
            TokenType.LEFTBRACE,
            TokenType.RIGHTBRACE,
        )
    }
}
