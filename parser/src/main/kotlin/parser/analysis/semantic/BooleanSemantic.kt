package parser.analysis.semantic

import token.Token
import token.TokenType

class BooleanSemantic : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        return tokenList.size == 1 && checkValue(tokenList.first())
    }

    private fun checkValue(token: Token): Boolean {
        return if (isLiteral(token)) {
            when (token.value) {
                "True", "False" -> true
                else -> false
            }
        } else { // if is not boolean it is a variable
            true
        }
    }

    private fun isLiteral(token: Token): Boolean {
        return token.type == TokenType.BOOLEAN_LITERAL
    }
}
