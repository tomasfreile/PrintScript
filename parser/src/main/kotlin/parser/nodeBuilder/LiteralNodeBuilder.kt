package parser.nodeBuilder

import ast.AstNode
import ast.LiteralNode
import token.Token

class LiteralNodeBuilder : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        return LiteralNode(tokenList[0].value, tokenList[0].type)
    }
}
