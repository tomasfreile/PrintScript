package parser.parse

import ast.AstNode
import token.Token

interface Parser {
    fun canHandle(tokenList: List<Token>): Boolean

    fun createAST(tokenList: List<Token>): AstNode
}
