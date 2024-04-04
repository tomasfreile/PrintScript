package parser.analysis.sintactic.commons

import parser.analysis.sintactic.IsAssignation
import parser.analysis.sintactic.IsDeclarative
import parser.analysis.sintactic.IsPrint
import parser.analysis.sintactic.SintacticChecker
import token.Token
import token.TokenType
import parser.utils.Type as Type

class HasInvalidOperator : SintacticChecker { // SOLO REVISA QUE SE MANTENGA EL FORMATO LITERAL OPERADOR LITERAL EN EL CONTENIDO (LOL RULE)
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        val type = getParseType(tokenList)
        return when (type) {
            Type.ASSIGNATION -> checkStructure(tokenList, 2, tokenList.size - 1) // THIS IS THE CONTENT
            Type.PRINT -> checkStructure(tokenList, 2, tokenList.size - 2)
            Type.DECLARATION -> checkStructure(tokenList, 5, tokenList.size - 1)
            Type.NIL -> false
        }
    }

    private fun checkStructure(
        tokenList: List<Token>,
        from: Int,
        to: Int,
    ): Boolean { // format: LITERAL OPERATOR LITERAL...
        val content = tokenList.subList(from, to)
        var index = 0
        while (index < content.size) {
            if (index + 1 > content.size) return true // there is a problem because at the end should be a Literal
            val token = content[index]
            index +=
                when {
                    isLiteral(token) || ignore(token) -> 1 // Literal o  = ( 3 + 3 ) * 4
                    isAnOperator(token) -> { // At least index should be index = 1
                        if (isFormatted(content, index)) 1 else return true
                    }
                    else -> return true
                }
        }
        return false
    }

    private fun isFormatted(
        content: List<Token>,
        index: Int,
    ): Boolean {
        return if (index + 1 >= content.size) {
            false
        } else {
            isLiteral(content[index - 1]) && isLiteral(content[index + 1]) || // L O L
                isRightParen(content[index - 1]) && isLiteral(content[index + 1]) || // ) O L
                isRightParen(content[index - 1]) && isLeftParen(content[index + 1]) || // ) O (
                isLiteral(content[index - 1]) && isLeftParen(content[index + 1]) // L O (
        }
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBER_LITERAL, TokenType.STRING_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }

    private fun isAnOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.STAR, TokenType.SLASH, TokenType.MINUS -> true
            else -> false
        }
    }

    private fun ignore(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }

    private fun isRightParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun getParseType(tokenList: List<Token>): Type {
        return when {
            IsDeclarative().checkSyntax(tokenList) -> Type.DECLARATION
            IsPrint().checkSyntax(tokenList) -> Type.PRINT
            IsAssignation().checkSyntax(tokenList) -> Type.ASSIGNATION
            else -> Type.NIL
        }
    }
}
