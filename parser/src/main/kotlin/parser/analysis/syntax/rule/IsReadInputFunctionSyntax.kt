package parser.analysis.syntax.rule

import token.Token
import token.TokenType

class IsReadInputFunctionSyntax : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        return when {
            hasEnoughLength(tokenList) -> checkStructure(tokenList)
            else -> false
        }
    }

    private fun checkStructure(expression: List<Token>): Boolean {
        var points = 0
        if (isFunction(expression[0])) points += 1
        if (isLeftParen(expression[1])) points += 1
        if (isRightParen(expression[3])) points += 1
        return points == 3
    }

    private fun isFunction(token: Token): Boolean {
        return when (token.type) {
            TokenType.READINPUT -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFTPAREN -> true
            else -> false
        }
    }

    private fun isRightParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHTPAREN -> true
            else -> false
        }
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size == 4
    }
}
