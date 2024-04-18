package parser.analysis.semantic.rule

import token.Token
import token.TokenType

class BooleanSemantic : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        return tokenList.size == 1 && isLiteral(tokenList.first())
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.BOOLEANLITERAL, TokenType.VALUEIDENTIFIERLITERAL -> true
            else -> false
        }
    }
}
