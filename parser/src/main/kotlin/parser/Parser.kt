package parser

import ast.Node
import token.Token
import token.TokenType

class Parser {
    fun parse(tokenList: List<Token>): Node? {
        if (!tokenList.isEmpty()) {
            val token = tokenList.first()
            when (token.type) {
                TokenType.VARIABLE_KEYWORD -> return DeclarationParse().parse(tokenList)
                else -> {}
            }
        }
        return null
    }
}
