package parser.nodeBuilder

import ast.AstNode
import token.Token

/*
    By the moment Boolean Builder is just a Literal Builder, but for future ;)
 */
class BooleanNodeBuilder : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        return buildLiteralNode(tokenList)
    }

    private fun buildLiteralNode(tokenList: List<Token>): AstNode {
        return LiteralNodeBuilder().build(tokenList)
    }
}
