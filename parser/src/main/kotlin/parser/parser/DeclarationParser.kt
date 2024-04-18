package parser.parser

import ast.AstNode
import ast.NilNode
import ast.VariableDeclarationNode
import parser.InvalidDeclarationStatement
import parser.analysis.Result
import parser.analysis.semantic.analyser.SemanticAnalyzer
import parser.analysis.syntax.analyzer.SyntaxAnalyzer
import parser.analysis.syntax.rule.common.HasSentenceDelimiter
import parser.nodeBuilder.NodeBuilder
import position.TokenPosition
import token.Token
import token.TokenType

class DeclarationParser(
    private val separator: TokenType,
    private val dataTypeMap: Map<TokenType, TokenType>,
    private val declarationType: List<TokenType>,
    private val syntaxAnalyzer: SyntaxAnalyzer,
    private val semanticAnalyzer: SemanticAnalyzer,
    private val nodeBuilders: Map<TokenType, NodeBuilder>,
) : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            preCondition(tokenList) -> isDeclaration(tokenList)
            else -> false
        }
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.size >= 4 &&
            HasSentenceDelimiter(separator).checkSyntax(tokenList)
    }

    override fun createAST(tokenList: List<Token>): AstNode? {
        return when {
            isUninitialized(tokenList) -> createUninitializedDeclarationAst(tokenList)
            else -> buildDeclarationAst(tokenList)
        }
    }

    private fun isUninitialized(tokenList: List<Token>): Boolean {
        return getContent(tokenList).isEmpty()
    }

    private fun isDeclaration(tokenList: List<Token>): Boolean {
        var points = 0
        if (hasValidDeclarationType(tokenList[0])) points += 1
        if (tokenList[1].type == TokenType.VALUEIDENTIFIERLITERAL) points += 1
        if (tokenList[2].type == TokenType.COLON) points += 1
        if (hasValidType(tokenList[3])) points += 1
        return points == 4
    }

    /*
        Checks if is LET or CONST
     */
    private fun hasValidDeclarationType(token: Token): Boolean {
        return when {
            declarationType.contains(token.type) -> true
            else -> false
        }
    }

    /*
        Checks if exists data type
     */
    private fun hasValidType(token: Token): Boolean {
        for ((_, type) in dataTypeMap) {
            when (type) {
                token.type -> return true
                else -> continue
            }
        }
        return false
    }

    private fun buildDeclarationAst(tokenList: List<Token>): AstNode {
        val content = getContent(tokenList)
        val contentType = getContentType(content)
        val result = isValidExpression(tokenList, content, contentType)
        return when (result) {
            Result.Ok ->
                createDeclarationAst(
                    tokenList,
                    buildNode(content),
                )
            else -> throw InvalidDeclarationStatement(
                "Invalid declaration on line: " + tokenList.first().start.row,
            )
        }
    }

    private fun isValidExpression(
        tokenList: List<Token>,
        content: List<Token>,
        contentType: TokenType,
    ): Result {
        val syntaxResult = syntaxAnalyzer.analyzeSyntax(content)
        return when (syntaxResult) {
            Result.Ok -> semanticAnalyzer.analyze(tokenList, contentType)
            else -> Result.NOTOK
        }
    }

    private fun buildNode(content: List<Token>): AstNode {
        val contentType = getContentType(content)
        val builder = nodeBuilders[contentType] ?: throw NullPointerException("Invalid builder for node")
        return builder.build(content)
    }

    private fun getContentType(content: List<Token>): TokenType {
        return syntaxAnalyzer.getSyntaxType(content)
    }

    private fun createUninitializedDeclarationAst(tokenList: List<Token>): AstNode {
        return createDeclarationAst(tokenList)
    }

    private fun createDeclarationAst(
        tokenList: List<Token>,
        node: AstNode = NilNode,
    ): AstNode {
        return VariableDeclarationNode(
            tokenList[0].type,
            tokenList[1].value,
            tokenList[3].type,
            node,
            TokenPosition(tokenList[1].start, tokenList[1].end),
        )
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return if (tokenList.size == 5) emptyList() else tokenList.subList(5, tokenList.size - 1) // expression without semicolon
    }
}
