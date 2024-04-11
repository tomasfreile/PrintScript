package parser.analysis.sintactic

import parser.InvalidOperatorException
import parser.analysis.semantic.OperatorIsFormatted
import token.Token
import token.TokenType

class IsStringExpression : SyntaxRule {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        if (!OperatorIsFormatted().checkSemantic(
                tokenList,
            )
        ) {
            throw InvalidOperatorException("Invalid Operator on line: " + tokenList.first().start.row.toString())
        }
        for (token in tokenList) {
            when {
                isInvalidOperator(
                    token,
                ) -> throw InvalidOperatorException(
                    "Invalid Operator on coord: " + "(" + token.start.row.toString() + "; " + token.start.column.toString() + ")",
                )
                isLiteral(token) || isPlus(token) -> continue
                else -> return false
            }
        }
        return true
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }

    private fun isPlus(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS -> true
            else -> false
        }
    }

    private fun isInvalidOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.SLASH, TokenType.MINUS, TokenType.STAR -> true
            else -> false
        }
    }
}
