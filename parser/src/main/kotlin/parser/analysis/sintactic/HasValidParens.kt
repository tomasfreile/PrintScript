package parser.analysis.sintactic

import token.Token
import token.TokenType

class HasValidParens : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        var index = 0
        var tokenCopy = tokenList
        while (index < tokenList.size) {
            when (tokenCopy[index].type) {
                TokenType.LEFT_PAREN -> {
                    val rightParenIndex = getRightParenIndex(tokenCopy)
                    if (rightParenIndex > 0) {
                        tokenCopy = tokenCopy.subList(rightParenIndex + 1, tokenList.size)
                        index = rightParenIndex + 1
                    } else {
                        return false
                    }
                }
                else -> continue
            }
        }
        return true
    }

    private fun getRightParenIndex(tokenList: List<Token>): Int {
        var index = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.RIGHT_PAREN -> return index
                else -> index += 1
            }
        }
        return -1
    }
}
