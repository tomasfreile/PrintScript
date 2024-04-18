package parser.analysis.semantic.rule.common

import parser.InvalidOperatorException
import parser.analysis.semantic.rule.SemanticRule
import token.Token
import token.TokenType

class HasValidValues(private val invalidValues: List<TokenType>) : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            when {
                invalidValues.contains(token.type) -> {
                    throw InvalidOperatorException(
                        "Invalid Operator on coord: (" + token.start.row + "; " + token.start.column + ")",
                    )
                }
                else -> continue
            }
        }
        return true
    }
}
