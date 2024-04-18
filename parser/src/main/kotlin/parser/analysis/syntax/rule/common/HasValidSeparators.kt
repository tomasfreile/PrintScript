package parser.analysis.syntax.rule.common

import parser.analysis.syntax.rule.SyntaxRule
import token.Token
import token.TokenType

class HasValidSeparators(
    private val left: TokenType,
    private val right: TokenType,
) : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return hasInvalidSeparator(tokenList, left, right)
    }

    private fun hasInvalidSeparator(
        content: List<Token>,
        left: TokenType,
        right: TokenType,
    ): Boolean {
        var i = 0
        var copyList = content
        while (i < copyList.size && copyList.isNotEmpty()) {
            val token = copyList[i]
            when (token.type) {
                left -> {
                    if (hasPair(content, left, right)) {
                        val rightTypeIndex = getTokenIndexByType(copyList, right)
                        copyList = copyList.subList(rightTypeIndex + 1, copyList.size)
                        i = 0
                        continue
                    } else {
                        return false
                    }
                }
                right -> return false
                else -> i++
            }
        }
        return true
    }

    private fun hasPair(
        content: List<Token>,
        left: TokenType,
        right: TokenType,
    ): Boolean {
        return HasPairSeparator(
            left,
            right,
        ).checkSyntax(content)
    }

    private fun getTokenIndexByType(
        tokenList: List<Token>,
        tokenType: TokenType,
    ): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                tokenType -> return i
                else -> i++
            }
        }
        return -1
    }
}
