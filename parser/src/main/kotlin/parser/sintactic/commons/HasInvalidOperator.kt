package parser.sintactic.commons

import parser.sintactic.IsAssignation
import parser.sintactic.IsDeclarative
import parser.sintactic.IsPrint
import parser.sintactic.SintacticChecker
import token.Token
import token.TokenType
import parser.Type as Type

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
            index +=
                when {
                    isLiteral(content[index]) -> 1
                    isAnOperator(content[index]) -> { // At least index should be index = 1
                        if (isFormatted(content, index)) {
                            1
                        } else {
                            return true
                        }
                    }
                    else -> return true
                }
        }
        return false
    }

    private fun isFormatted(
        content: List<Token>,
        index: Int,
    ): Boolean { // L O L jsjs
        return if (index + 1 >= content.size) false else isLiteral(content[index - 1]) && isLiteral(content[index + 1])
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBER, TokenType.STRING, TokenType.VALUE_IDENTIFIER -> true
            else -> false
        }
    }

    private fun isAnOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.STAR, TokenType.SLASH, TokenType.MINUS -> true
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
