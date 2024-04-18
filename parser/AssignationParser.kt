package parser.parser

import ast.AssignmentNode
import ast.AstNode
import parser.InvalidAssignationException
import parser.analysis.Result
import parser.analysis.semantic.analyser.SemanticAnalyzer
import parser.analysis.syntax.analyzer.SyntaxAnalyzer
import parser.analysis.syntax.rule.common.HasSentenceDelimiter
import parser.nodeBuilder.NodeBuilder
import token.Token
import token.TokenType

class AssignationParser(
    private val separator: TokenType,
    private val syntaxAnalyzer: SyntaxAnalyzer,
    private val semanticAnalyzer: SemanticAnalyzer,
    private val nodeBuilders: Map<TokenType, NodeBuilder>,
) : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            preCondition(tokenList) -> isAssignation(tokenList)
            else -> false
        }
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        val content = getContent(tokenList)
        val contentType = getContentType(content)
        val result = getResult(tokenList, contentType)
        return when (result) {
            Result.Ok -> {
                createAssignationAst(
                    tokenList,
                    createContentNode(content, contentType),
                    contentType,
                )
            }
            else -> throw InvalidAssignationException(
                "Invalid assignation on line: " + tokenList.first().start.row,
            )
        }
    }

    private fun getContentType(content: List<Token>): TokenType {
        return syntaxAnalyzer.getSyntaxType(content)
    }

    private fun getResult(
        content: List<Token>,
        contentType: TokenType,
    ): Result {
        return semanticAnalyzer.analyze(content, contentType)
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.size >= 3 &&
            HasSentenceDelimiter(separator).checkSyntax(tokenList)
    }

    private fun isAssignation(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.VALUEIDENTIFIERLITERAL) points += 1
        if (tokenList[1].type == TokenType.ASSIGNATION) points += 1
        return points == 2
    }

    private fun createContentNode(
        expression: List<Token>,
        type: TokenType,
    ): AstNode {
        val builder = nodeBuilders[type] ?: throw NullPointerException("Build Node problem in Assgnation")
        return builder.build(expression)
    }

    private fun createAssignationAst(
        tokenList: List<Token>,
        node: AstNode,
        valueType: TokenType,
    ): AstNode {
        return AssignmentNode(
            tokenList[0].value,
            node,
            valueType,
            node.position,
        )
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size - 1)
    }
}
