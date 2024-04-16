package parser.analysis.semantic.common

import parser.analysis.semantic.SemanticRule
import token.Token
import token.TokenType

class IsNested(
    private val expressionType: TokenType,
) : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            when (token.type) {
                expressionType -> return true
                else -> continue
            }
        }
        return false
    }
}
