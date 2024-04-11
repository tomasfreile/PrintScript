package parser.analysis.semantic.common

import parser.InvalidOperatorException
import parser.analysis.semantic.SemanticRule
import token.Token
import token.TokenType

class HasValidOperators(private val invalidOperators: List<TokenType>) : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            when {
                isInvalid(token) -> {
                    throw InvalidOperatorException(
                        "Invalid Operator on coord: (" + token.start.row + "; " + token.start.column + ")",
                    )
                }
                else -> continue
            }
        }
        return true
    }

    private fun isInvalid(token: Token): Boolean {
        for (operatorType in invalidOperators) {
            when (token.type) {
                operatorType -> return true
                else -> continue
            }
        }
        return false
    }
}
