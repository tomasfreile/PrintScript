package parser.parser

import ast.AssignmentNode
import ast.AstNode
import parser.InvalidAssignationException
import parser.analysis.semantic.SemanticRule
import parser.analysis.syntax.SyntaxRule
import parser.analysis.syntax.common.HasSentenceSeparator
import parser.nodeBuilder.NodeBuilder
import token.Token
import token.TokenType

class AssignationParser(
    private val separator: TokenType,
    private val expressionsSyntax: Map<TokenType, SyntaxRule>,
    private val expressionsSemantic: Map<TokenType, SemanticRule>,
    private val nodeBuilders: Map<TokenType, NodeBuilder>,
) : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            preCondition(tokenList) -> isAssignation(tokenList)
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
                            createAssignationAst(
                                tokenList,
                                it,
                            )
                        }
                    }
                }
                else -> continue
            }
        }
        throw InvalidAssignationException(
            "Invalid assignation on line: " + tokenList.first().start.row,
        )
    }

    private fun isExpressionValid(
        tokenType: TokenType,
        content: List<Token>,
    ): Boolean {
        val syntax = expressionsSyntax[tokenType]
        return syntax != null && syntax.checkSyntax(content)
    }

    private fun isSemanticValid(
        tokenType: TokenType,
        content: List<Token>,
    ): Boolean {
        val semantic = expressionsSemantic[tokenType]
        return semantic != null && semantic.checkSemantic(content)
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.size >= 3 &&
            HasSentenceSeparator(separator).checkSyntax(tokenList)
    }

    private fun isAssignation(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.VALUE_IDENTIFIER_LITERAL) points += 1
        if (tokenList[1].type == TokenType.ASSIGNATION) points += 1
        return points == 2
    }

    private fun buildNode(
        expression: List<Token>,
        type: TokenType,
    ): AstNode? {
        val builder = nodeBuilders[type]
        return builder?.build(expression)
    }

    private fun createAssignationAst(
        tokenList: List<Token>,
        node: AstNode,
    ): AstNode {
        return AssignmentNode(
            tokenList[0].value,
            node,
        )
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size - 1)
    }
}
