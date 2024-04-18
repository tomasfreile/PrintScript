@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parser

import ast.*
import parser.CodeBlockSeparator
import parser.InvalidIfStatementException
import parser.analysis.semantic.rule.SemanticRule
import parser.analysis.semantic.rule.common.IsNested
import parser.analysis.syntax.rule.SyntaxRule
import parser.analysis.syntax.rule.ifSyntax.IsIfElseSyntax
import parser.nodeBuilder.CodeBlockNodeBuilder
import parser.nodeBuilder.LiteralNodeBuilder
import position.TokenPosition
import token.Token
import token.TokenType

class IfParser(
    private val parserList: List<Parser>,
    private val codeBlockSyntax: SyntaxRule,
    private val codeBlockSemantic: SemanticRule,
) : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return isIfElse(tokenList)
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        val elseContent = getElseContent(tokenList)
        val thenContent = getThenContent(tokenList, elseContent.isNotEmpty())
        return when {
            checkBlock(elseContent, thenContent) -> {
                createIfNode(
                    tokenList,
                    createBlock(thenContent),
                    createBlock(elseContent),
                )
            }
            else -> throw InvalidIfStatementException(
                "Invalid content in block on line" + tokenList.first().start.row.toString(),
            )
        }
    }

    private fun checkBlock(
        elseContent: List<Token>,
        thenContent: List<Token>,
    ): Boolean {
        return checkBlockSyntax(elseContent, thenContent) &&
            checkBlockSemantic(elseContent, thenContent)
    }

    private fun checkBlockSyntax(
        elseContent: List<Token>,
        thenContent: List<Token>,
    ): Boolean {
        return when {
            elseContent.isNotEmpty() -> {
                codeBlockSyntax.checkSyntax(elseContent) &&
                    codeBlockSyntax.checkSyntax(thenContent)
            }
            else -> codeBlockSyntax.checkSyntax(thenContent)
        }
    }

    private fun checkBlockSemantic(
        elseContent: List<Token>,
        thenContent: List<Token>,
    ): Boolean {
        return when {
            elseContent.isNotEmpty() -> {
                codeBlockSemantic.checkSemantic(thenContent) &&
                    codeBlockSemantic.checkSemantic(elseContent)
            }
            else -> codeBlockSemantic.checkSemantic(thenContent)
        }
    }

    private fun getThenContent(
        tokenList: List<Token>,
        hasElseContent: Boolean,
    ): List<Token> {
        return when {
            hasElseContent -> {
                val elseIndex = getElseIndex(tokenList)
                tokenList.subList(5, elseIndex - 1)
            }
            else -> tokenList.subList(5, tokenList.size - 1)
        }
    }

    private fun getElseContent(tokenList: List<Token>): List<Token> {
        return if (hasElseBlock(tokenList)) {
            val elseIndex = getElseIndex(tokenList)
            tokenList.subList(elseIndex + 2, tokenList.size - 1)
        } else {
            emptyList()
        }
    }

    private fun isIfElse(tokenList: List<Token>): Boolean {
        return IsIfElseSyntax().checkSyntax(tokenList)
    }

    private fun hasElseBlock(tokenList: List<Token>): Boolean {
        return getElseIndex(tokenList) > 0
    }

    private fun createBlock(content: List<Token>): AstNode {
        return CodeBlockNodeBuilder(
            addHimselfIntoParserList(),
            getExpressionsByIndex(content),
        ).build(content)
    }

    private fun createIfNode(
        tokenList: List<Token>,
        thenBlock: AstNode,
        elseBlock: AstNode,
    ): AstNode {
        return IfNode(
            createLiteralNode(tokenList[2]),
            thenBlock,
            elseBlock,
            TokenPosition(tokenList.first().start, tokenList.first().start),
        )
    }

    private fun getExpressionsByIndex(tokenList: List<Token>): List<Pair<Int, Int>> {
        return CodeBlockSeparator(TokenType.SEMICOLON).separateContentByIndexes(tokenList)
    }

    private fun createLiteralNode(token: Token): LiteralNode {
        return LiteralNodeBuilder().build(listOf(token))
    }

    private fun getElseIndexInNestedIf(tokenList: List<Token>): Int {
        var actual = 4
        while (actual < tokenList.size) {
            val token = tokenList[actual]
            when (token.type) {
                TokenType.IF -> {
                    val skipTo = skipToIndex(tokenList, actual)
                    when {
                        skipTo >= 0 -> actual = skipTo
                        else -> break
                    }
                }
                TokenType.ELSE -> {
                    /*
                        If syntax is ok, for sure there is smth wrong
                     */
                    if (isIfElse(tokenList.subList(0, actual + 1))) break else return actual
                }
                else -> actual += 1
            }
        }
        return -1
    }

    private fun getElseIndexNotNestedIf(tokenList: List<Token>): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.ELSE -> return i
                else -> i++
            }
        }
        return -1
    }

    private fun skipToIndex(
        tokenList: List<Token>,
        from: Int,
    ): Int {
        var i = from + 1
        /*
            skip if statements until is an If
         */
        while (!isIfElse(tokenList.subList(from, i)) && i < tokenList.size) i += 1
        return when {
            i >= tokenList.size -> -1
            else -> i
        }
    }

    private fun isNested(tokenList: List<Token>): Boolean {
        return IsNested(TokenType.IF).checkSemantic(tokenList.subList(1, tokenList.size))
    }

    private fun getElseIndex(tokenList: List<Token>): Int {
        return when {
            isNested(tokenList.subList(4, tokenList.size)) -> getElseIndexInNestedIf(tokenList)
            else -> getElseIndexNotNestedIf(tokenList)
        }
    }

    private fun addHimselfIntoParserList(): List<Parser> {
        val result = mutableListOf<Parser>()
        result.addAll(parserList)
        result.add(this)
        return result.toList()
    }
}
