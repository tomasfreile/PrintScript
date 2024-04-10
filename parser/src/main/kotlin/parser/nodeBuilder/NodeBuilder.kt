package parser.nodeBuilder

import ast.AstNode
import token.Token

interface NodeBuilder {
    fun build(tokenList: List<Token>): AstNode
}
