package parser

import parser.analysis.syntax.rule.ifSyntax.IsIfElseSyntax
import token.Token
import token.TokenType

class CodeBlockSeparator(private val delimiterToken: TokenType) {
    fun separateContentByIndexes(content: List<Token>): List<Pair<Int, Int>> {
        var i = 0
        val result = mutableListOf<Pair<Int, Int>>()
        while (i < content.size) {
            val nextIndex = getExpressionEndIndex(content, i)
            result.add(Pair(i, nextIndex))
            i = nextIndex
        }
        return result.toList()
    }

    private fun getExpressionEndIndex(
        content: List<Token>,
        from: Int,
    ): Int {
        return when {
            isIf(content[from]) -> getLastIndexInIfExpression(content, from)
            else -> getLastIndexFromExpression(content, from)
        }
    }

    private fun getLastIndexFromExpression(
        content: List<Token>,
        from: Int,
    ): Int {
        var i = from
        while (i < content.size) {
            val token = content[i]
            when (token.type) {
                delimiterToken -> return i + 1
                else -> i++
            }
        }
        throw InvalidSyntaxException(
            "Invalid Expression on line: " + content.first().start.row.toString(),
        )
    }

    private fun getLastIndexInIfExpression(
        content: List<Token>,
        from: Int,
    ): Int {
        var i = from + 1
        while (i < content.size && !checkIfSyntax(content.subList(from, i))) i++ // while is not an If Else of If...
        return when {
            i > content.size -> throw InvalidSyntaxException(
                "Invalid Expression on line: " + content.first().start.row.toString(),
            )
            else -> i
        }
    }

    private fun isIf(token: Token): Boolean {
        return token.type == TokenType.IF
    }

    private fun checkIfSyntax(tokenList: List<Token>): Boolean {
        return IsIfElseSyntax().checkSyntax(tokenList)
    }
}
