package parser.analysis.semantic

import token.Token
import token.TokenType

class OperatorIsFormatted : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        var index = 0
        for (token in tokenList) {
            index +=
                when {
                    isLiteral(token) -> 1
                    isOperator(token) -> if (isFormatted(tokenList, index)) 1 else return false
                    else -> return false
                }
        }
        return true
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL, TokenType.BOOLEAN_LITERAL -> true
            else -> false
        }
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS -> true
            else -> false
        }
    }

    private fun isFormatted(
        tokenList: List<Token>,
        index: Int,
    ): Boolean { // L O L ;)
        if (index + 1 >= tokenList.size) return false
        return isLiteral(tokenList[index - 1]) && isLiteral(tokenList[index + 1])
    }
}
