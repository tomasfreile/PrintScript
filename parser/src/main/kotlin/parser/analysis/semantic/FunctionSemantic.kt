package parser.analysis.semantic

import token.Token
import token.TokenType

class FunctionSemantic : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        val value = getValue(tokenList)
        return value.type == TokenType.STRING_LITERAL
    }

    private fun getValue(tokenList: List<Token>): Token {
        return tokenList[2]
    }
}