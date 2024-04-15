package parser.analysis.syntax.common

import parser.analysis.syntax.SyntaxRule
import token.Token
import token.TokenType

class HasPairOfParen : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        var index = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.LEFTPAREN -> {
                    val rightParenIndex = getRightParenIndex(tokenList, index)
                    return rightParenIndex > 0
                }
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
                TokenType.RIGHTPAREN -> return index
                else -> index += 1
            }
        }
        return -1
    }
}
