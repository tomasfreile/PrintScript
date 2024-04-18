package parser.parser

import ast.AstNode
import ast.PrintNode
import parser.InvalidSyntaxException
import parser.analysis.Result
import parser.analysis.semantic.analyser.SemanticAnalyzer
import parser.analysis.syntax.analyzer.SyntaxAnalyzer
import parser.analysis.syntax.rule.common.HasSentenceDelimiter
import parser.nodeBuilder.NodeBuilder
import token.Token
import token.TokenType

class PrintParser(
    private val separator: TokenType,
    private val syntaxAnalyzer: SyntaxAnalyzer,
    private val semanticAnalyzer: SemanticAnalyzer,
    private val nodeBuilders: Map<TokenType, NodeBuilder>,
) : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            preCondition(tokenList) -> isPrint(tokenList)
            else -> false
        }
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        val content = getContent(tokenList)
        val contentType = getContentType(content)
        val result = getResult(tokenList, contentType)
        return when (result) {
            Result.Ok -> {
                val node = createContentNode(content, contentType)
                createPrintAst(node)
            }
            else -> throw InvalidSyntaxException(
                "Invalid print on line: " + tokenList.first().start.row,
            )
        }
    }

    private fun getContentType(content: List<Token>): TokenType {
        return syntaxAnalyzer.getSyntaxType(content)
    }

    private fun getResult(
        tokenList: List<Token>,
        contentType: TokenType,
    ): Result {
        return semanticAnalyzer.analyze(tokenList, contentType)
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.size >= 4 &&
            HasSentenceDelimiter(separator).checkSyntax(tokenList)
    }

    private fun isPrint(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.PRINT) points += 1
        if (tokenList[1].type == TokenType.LEFTPAREN) points += 1
        if (tokenList[tokenList.size - 2].type == TokenType.RIGHTPAREN) points += 1
        return points == 3
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size - 2)
    }

    private fun createContentNode(
        content: List<Token>,
        contentType: TokenType,
    ): AstNode {
        val builder = nodeBuilders[contentType] ?: throw NullPointerException("Invalid content type")
        return builder.build(content)
    }

    private fun createPrintAst(node: AstNode): AstNode {
        return PrintNode(node, node.position)
    }
}
