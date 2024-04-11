package parser.parser

import ast.AstNode
import ast.NilNode
import ast.VariableDeclarationNode
import parser.InvalidDeclarationStatement
import parser.analysis.semantic.SemanticRule
import parser.analysis.syntax.SyntaxRule
import parser.analysis.syntax.common.HasSentenceSeparator
import parser.nodeBuilder.NodeBuilder
import token.Token
import token.TokenType

class DeclarationParser(
    private val separator: TokenType,
    private val dataTypeMap: Map<TokenType, TokenType>,
    private val declarationType: List<TokenType>,
    private val expressionsSyntax: Map<TokenType, SyntaxRule>,
    private val expressionsSemantic: Map<TokenType, SemanticRule>,
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
            HasSentenceSeparator(separator).checkSyntax(tokenList)
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
        if (tokenList[1].type == TokenType.VALUE_IDENTIFIER_LITERAL) points += 1
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

    private fun getExpressionSyntax(token: Token): SyntaxRule? {
        return expressionsSyntax[token.type]
    }

    private fun getSemantic(token: Token): SemanticRule? {
        return expressionsSemantic[token.type]
    }

    private fun buildDeclarationAst(tokenList: List<Token>): AstNode? {
        return when {
            isValidDeclaration(tokenList) -> {
                val content = getContent(tokenList)
                buildNode(content, tokenList[3])?.let {
                    createDeclarationAst(
                        tokenList,
                        it,
                    )
                }
            }
            else -> throw InvalidDeclarationStatement(
                "Invalid declaration statement on line: " + tokenList.first().start.row,
            )
        }
    }

    private fun isValidDeclaration(tokenList: List<Token>): Boolean {
        val expressionSyntax = getExpressionSyntax(tokenList[3])
        val expressionSemantic = getSemantic(tokenList[3])
        val content = getContent(tokenList)
        return when {
            expressionSyntax != null && expressionSemantic != null -> {
                validate(expressionSyntax, expressionSemantic, content)
            }
            else -> false
        }
    }

    private fun validate(
        expressionSyntax: SyntaxRule,
        expressionSemantic: SemanticRule,
        content: List<Token>,
    ): Boolean {
        val syntaxResult = expressionSyntax.checkSyntax(content)
        val semanticResult = expressionSemantic.checkSemantic(content)
        return syntaxResult && semanticResult
    }

    private fun buildNode(
        content: List<Token>,
        token: Token,
    ): AstNode? {
        val builder = nodeBuilders[token.type]
        return builder?.build(content)
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
            node.position,
        )
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return if (tokenList.size == 5) emptyList() else tokenList.subList(5, tokenList.size - 1) // expression without semicolon
    }
}
