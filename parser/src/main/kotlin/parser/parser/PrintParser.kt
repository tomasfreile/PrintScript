package parser.parser

import ast.AstNode
import ast.PrintNode
import parser.analysis.sintactic.IsArithmeticExpression
import parser.analysis.sintactic.IsStringConcat
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.LiteralNodeBuilder
import parser.nodeBuilder.StringNodeBuilder
import token.Token
import token.TokenType

class PrintParser : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            isEnoughSize(tokenList) -> isPrint(tokenList)
            else -> false
        }
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        val content = getExpression(tokenList)
        return when {
            isArithmeticExpression(content) -> createArithmeticBinaryNode(content)
            isConcat(content) -> createBinaryNode(content)
            else -> createLiteralNode(content)
        }
    }

    private fun isEnoughSize(tokenList: List<Token>): Boolean {
        return tokenList.size >= 4
    }

    private fun isPrint(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.PRINT) points += 1
        if (tokenList[1].type == TokenType.LEFT_PAREN) points += 1
        if (tokenList[tokenList.size - 2].type == TokenType.RIGHT_PAREN) points += 1
        return points == 3
    }

    private fun getExpression(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size - 2)
    }

    private fun isConcat(expression: List<Token>): Boolean {
        return when {
            expression.size > 1 -> IsStringConcat().checkSyntax(expression)
            else -> false
        }
    }

    private fun isArithmeticExpression(expression: List<Token>): Boolean {
        return IsArithmeticExpression().checkSyntax(expression)
    }

    private fun createBinaryNode(expression: List<Token>): AstNode {
        return PrintNode(StringNodeBuilder().build(expression))
    }

    private fun createArithmeticBinaryNode(expression: List<Token>): AstNode {
        return PrintNode(ArithmeticNodeBuilder().build(expression))
    }

    private fun createLiteralNode(expression: List<Token>): AstNode {
        return PrintNode(LiteralNodeBuilder().build(expression))
    }
}
