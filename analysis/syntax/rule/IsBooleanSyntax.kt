package parser.analysis.syntax.rule

import token.Token
import token.TokenType

/*
    By the moment Boolean expressions has no operators
 */
class IsBooleanSyntax : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            when {
                isLiteral(token) -> continue
                else -> return false
            }
        }
        return true
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.BOOLEANLITERAL, TokenType.VALUEIDENTIFIERLITERAL -> true
            else -> false
        }
    }
}
