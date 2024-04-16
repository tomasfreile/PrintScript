package parser.nodeBuilder

import ast.AstNode
import ast.CodeBlock
import ast.NilNode
import parser.InvalidExpressionException
import parser.parser.Parser
import position.TokenPosition
import token.Token

class CodeBlockNodeBuilder(
    private val parserList: List<Parser>,
    private val expressionsList: List<Pair<Int, Int>>,
) : NodeBuilder {
    override fun build(tokenList: List<Token>): AstNode {
        if (expressionsList.isEmpty()) {
            return NilNode
        }
        val astList = mutableListOf<AstNode>()
        for (pair in expressionsList) {
            val concreteExpression = getExpression(tokenList, pair)
            val node = getNodeFormExpression(concreteExpression)
            if (node != null) {
                astList.add(node)
            }
        }
        return CodeBlock(
            astList.toList(),
            TokenPosition(tokenList.first().start, tokenList.first().end),
        )
    }

    private fun getNodeFormExpression(expression: List<Token>): AstNode? {
        for (parser in parserList) {
            when {
                parser.canHandle(expression) -> return parser.createAST(expression)
                else -> continue
            }
        }
        throw InvalidExpressionException(
            "Invalid expression on line: " + expression.first().start.row,
        )
    }

    private fun getExpression(
        tokenList: List<Token>,
        indexes: Pair<Int, Int>,
    ): List<Token> {
        return tokenList.subList(indexes.first, indexes.second)
    }
}
