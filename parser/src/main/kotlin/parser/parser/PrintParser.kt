package parser.parser

import ast.AstNode
import ast.PrintNode
import parser.InvalidSyntaxException
import parser.analysis.sintactic.IsArithmeticExpression
import parser.analysis.sintactic.IsBooleanExpression
import parser.analysis.sintactic.IsStringExpression
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.BooleanNodeBuilder
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
            isBooleanExpression(content) -> createBooleanNode(content)
            isLiteral(content) -> createLiteralNode(content)
            else -> throw InvalidSyntaxException("Invalid Print Syntax on line: " + tokenList.first().start.row)
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
            expression.size > 1 -> IsStringExpression().checkSyntax(expression)
            else -> false
        }
    }

    private fun isLiteral(expression: List<Token>): Boolean {
        return if (expression.size == 1) {
            when (expression.first().type) {
                TokenType.STRING_LITERAL, TokenType.BOOLEAN_LITERAL, TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
                else -> false
            }
        } else {
            false
        }
    }

    private fun isArithmeticExpression(expression: List<Token>): Boolean {
        return IsArithmeticExpression().checkSyntax(expression)
    }

    private fun isBooleanExpression(expression: List<Token>): Boolean {
        return IsBooleanExpression().checkSyntax(expression)
    }

    private fun createBinaryNode(expression: List<Token>): AstNode {
        return PrintNode(StringNodeBuilder().build(expression))
    }

    private fun createBooleanNode(expression: List<Token>): AstNode {
        return PrintNode(BooleanNodeBuilder().build(expression))
    }

    private fun createArithmeticBinaryNode(expression: List<Token>): AstNode {
        return PrintNode(ArithmeticNodeBuilder().build(expression))
    }

    private fun createLiteralNode(expression: List<Token>): AstNode {
        return PrintNode(LiteralNodeBuilder().build(expression))
    }
}
