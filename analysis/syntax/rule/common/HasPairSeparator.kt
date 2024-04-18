package parser.analysis.syntax.rule.common

import parser.analysis.syntax.rule.SyntaxRule
import token.Token
import token.TokenType

class HasPairSeparator(
    val leftType: TokenType,
    val rightType: TokenType,
) : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        var index = 0
        for (token in tokenList) {
            when (token.type) {
                leftType -> {
                    val rightParenIndex = getRightParenIndex(tokenList, index)
                    return rightParenIndex > 0
                }
                rightType -> return false
                else -> index += 1
            }
        }
        return true // there is no left paren so all ok
    }

    private fun getRightParenIndex(
        tokenList: List<Token>,
        from: Int,
    ): Int {
        var index = 0
        for (token in tokenList.subList(from, tokenList.size)) {
            when (token.type) {
                rightType -> return index
                else -> index += 1
            }
        }
        return -1
    }
}
