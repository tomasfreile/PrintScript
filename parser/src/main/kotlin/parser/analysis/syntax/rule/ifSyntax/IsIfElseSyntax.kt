package parser.analysis.syntax.rule.ifSyntax

import parser.analysis.semantic.rule.common.IsNested
import parser.analysis.syntax.rule.SyntaxRule
import token.Token
import token.TokenType

class IsIfElseSyntax : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return when {
            hasEnoughLength(tokenList) -> checkStructure(tokenList)
            else -> false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size > 7
    }

    private fun checkStructure(tokenList: List<Token>): Boolean {
        val elseExpression = getElseExpression(tokenList) // if it has else or not is easier : )
        val ifExpression = getIfExpression(tokenList, elseExpression.isEmpty())
        return when {
            elseExpression.isEmpty() && isIfSyntax(ifExpression) -> true
            else -> isElseSyntax(elseExpression) && isIfSyntax(ifExpression)
        }
    }

    private fun isIfSyntax(tokenList: List<Token>): Boolean {
        return IfSyntax().checkSyntax(tokenList)
    }

    private fun isElseSyntax(tokenList: List<Token>): Boolean {
        return ElseSyntax().checkSyntax(tokenList)
    }

    private fun getIfExpression(
        tokenList: List<Token>,
        hasNotElseBlock: Boolean,
    ): List<Token> {
        return when {
            /*
                when does not have else block, is just an If or nothing
             */
            hasNotElseBlock -> tokenList
            else -> {
                /*
                    when has an else block, I just need the one before elseIndex
                 */
                val elseIndex = getElseIndex(tokenList)
                return tokenList.subList(0, elseIndex)
            }
        }
    }

    private fun getElseExpression(tokenList: List<Token>): List<Token> {
        if (hasElseExpression(tokenList)) {
            val elseIndex: Int =
                when {
                    isNested(tokenList) -> getElseIndex(tokenList)
                    else -> getElseIndex(tokenList)
                }
            return tokenList.subList(elseIndex, tokenList.size)
        }
        return emptyList()
    }

    private fun hasElseExpression(tokenList: List<Token>): Boolean {
        return getElseIndex(tokenList) > 0
    }

    private fun getElseIndexInNestedIf(tokenList: List<Token>): Int {
        var actual = 4
        while (actual < tokenList.size) {
            val token = tokenList[actual]
            when (token.type) {
                TokenType.IF -> {
                    val skipTo = skipToIndex(tokenList, actual)
                    when {
                        skipTo >= 0 -> actual = skipTo
                        else -> break
                    }
                }
                TokenType.ELSE -> {
                    /*
                        If syntax is ok, for sure there is smth wrong
                     */
                    if (checkSyntax(tokenList.subList(0, actual))) break else return actual
                }
                else -> actual += 1
            }
        }
        return -1
    }

    private fun getElseIndexNotNestedIf(tokenList: List<Token>): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.ELSE -> return i
                else -> i++
            }
        }
        return -1
    }

    private fun skipToIndex(
        tokenList: List<Token>,
        from: Int,
    ): Int {
        var i = from + 1
        /*
            skip if statements until is an If
         */
        while (!checkSyntax(tokenList.subList(from, i)) && i < tokenList.size) i += 1
        return when {
            i >= tokenList.size -> -1
            else -> i
        }
    }

    private fun isNested(tokenList: List<Token>): Boolean {
        return IsNested(TokenType.IF).checkSemantic(tokenList.subList(1, tokenList.size))
    }

    private fun getElseIndex(tokenList: List<Token>): Int {
        return when {
            isNested(tokenList.subList(4, tokenList.size)) -> getElseIndexInNestedIf(tokenList)
            else -> getElseIndexNotNestedIf(tokenList)
        }
    }
}
