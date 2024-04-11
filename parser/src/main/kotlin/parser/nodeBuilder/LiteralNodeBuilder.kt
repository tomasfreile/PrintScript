package parser.nodeBuilder

import ast.AstNode
import ast.LiteralNode
import position.TokenPosition
import token.Token

class LiteralNodeBuilder : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        return LiteralNode(tokenList[0].value, tokenList[0].type, TokenPosition(tokenList[0].start, tokenList[0].end))
    }
}
