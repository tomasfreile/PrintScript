package parser.analysis.syntax.rule.common

import parser.analysis.syntax.rule.SyntaxRule
import token.Token
import token.TokenType

class HasOperationAfterParen : SyntaxRule {
    /*
        Precondition: List should start with Left Paren, like (3 + 5) * 3
        Just analyse that after paren has an operation
     */
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        val splitedContet = splitContent(tokenList)
        return when {
            preCondition(splitedContet) -> {
                isOperator(splitedContet[0]) && isLiteral(splitedContet[1]) ||
                    isOperator(splitedContet[0]) && isLeftParen(splitedContet[1])
            }
            else -> false
        }
    }

    private fun splitContent(tokenList: List<Token>): List<Token> {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.RIGHTPAREN -> break
                else -> i += 1
            }
        }
        return tokenList.subList(i + 1, tokenList.size) // extracts (3 + 5) and provides * 3
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.MINUS, TokenType.SLASH, TokenType.STAR -> true
            else -> false
        }
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBERLITERAL, TokenType.VALUEIDENTIFIERLITERAL -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFTPAREN -> true
            else -> false
        }
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.size >= 2
    }
}
