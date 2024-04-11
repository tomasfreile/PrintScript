package parser.parser

import ast.AstNode
import ast.PrintNode
import parser.InvalidSyntaxException
import parser.analysis.semantic.SemanticRule
import parser.analysis.syntax.expression.Expression
import parser.analysis.syntax.rule.HasSentenceSeparator
import parser.nodeBuilder.NodeBuilder
import token.Token
import token.TokenType

class PrintParser(
    private val separator: TokenType,
    private val expressionsSyntax: Map<TokenType, Expression>,
    private val expressionsSemantic: Map<TokenType, SemanticRule>,
    private val nodeBuilders: Map<TokenType, NodeBuilder>,
) : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            preCondition(tokenList) -> isPrint(tokenList)
            else -> false
        }
    }

    override fun createAST(tokenList: List<Token>): AstNode? {
        val content = getContent(tokenList)
        for ((type, _) in expressionsSyntax) {
            when {
                isExpressionValid(type, content) -> {
                    if (isSemanticValid(type, content)) {
                        return buildNode(content, type)?.let {
                            createPrintAst(it)
                        }
                    }
                }
                else -> continue
            }
        }
        throw InvalidSyntaxException(
            "Invalid print on line: " + tokenList.first().start.row,
        )
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.size >= 4 &&
            HasSentenceSeparator(separator).checkSyntax(tokenList)
    }

    private fun isSemanticValid(
        tokenType: TokenType,
        content: List<Token>,
    ): Boolean {
        val semantic = expressionsSemantic[tokenType]
        return semantic != null && semantic.checkSemantic(content)
    }

    private fun isExpressionValid(
        tokenType: TokenType,
        content: List<Token>,
    ): Boolean {
        val syntax = expressionsSyntax[tokenType]
        return syntax != null && syntax.isExpression(content)
    }

    private fun isPrint(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.PRINT) points += 1
        if (tokenList[1].type == TokenType.LEFT_PAREN) points += 1
        if (tokenList[tokenList.size - 2].type == TokenType.RIGHT_PAREN) points += 1
        return points == 3
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size - 2)
    }

    private fun buildNode(
        expression: List<Token>,
        type: TokenType,
    ): AstNode? {
        val builder = nodeBuilders[type]
        return builder?.build(expression)
    }

    private fun createPrintAst(node: AstNode): AstNode {
        return PrintNode(node)
    }
}
