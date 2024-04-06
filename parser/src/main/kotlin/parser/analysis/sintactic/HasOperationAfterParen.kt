package parser.analysis.sintactic

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
            splitedContet.size >= 2 -> {
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
                TokenType.RIGHT_PAREN -> break
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
            TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }
}
