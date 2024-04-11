package parser.analysis.semantic.common

import parser.analysis.semantic.SemanticRule
import token.Token
import token.TokenType

class HasZeroDivision : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        TODO("Not yet implemented")
    }

    private fun getOperatorIndexByType(
        tokenList: List<Token>,
        type: TokenType,
    ): Int {
        val i = 0
        for (token in tokenList) {
            when (token.type) {
                type -> return i
                else -> continue
            }
        }
        return -1
    }
}
