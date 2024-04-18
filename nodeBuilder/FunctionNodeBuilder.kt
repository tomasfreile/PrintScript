package parser.nodeBuilder

import ast.AstNode
import ast.FunctionNode
import position.TokenPosition
import token.Token

class FunctionNodeBuilder : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        return FunctionNode(tokenList[0].type, buildLiteralNode(tokenList), TokenPosition(tokenList.first().start, tokenList.last().end))
    }

    private fun buildLiteralNode(tokenList: List<Token>): AstNode {
        return LiteralNodeBuilder().build(getExpressionNode(tokenList))
    }

    private fun getExpressionNode(tokenList: List<Token>): List<Token> {
        return listOf(tokenList[2])
    }
}
