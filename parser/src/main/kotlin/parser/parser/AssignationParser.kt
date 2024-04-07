package parser.parser

import ast.AssignmentNode
import ast.AstNode
import parser.InvalidSyntaxException
import parser.analysis.semantic.OperatorIsFormatted
import parser.analysis.sintactic.IsArithmeticExpression
import parser.analysis.sintactic.IsStringConcat
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.ContentNodeBuilder
import token.Token
import token.TokenType

class AssignationParser : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            hasEnoughLength(tokenList) -> isAssignation(tokenList)
            else -> false
        }
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        return when {
            isNumberExpression(tokenList) -> createArithmeticNode(tokenList)
            isStringExpression(tokenList) -> createStringNode(tokenList)
            else -> throw InvalidSyntaxException("Invalid Syntax Assignation on line: " + tokenList.first().start.row)
        }
    }

    private fun isAssignation(tokenList: List<Token>): Boolean {
        var points = 0
        if (tokenList[0].type == TokenType.VALUE_IDENTIFIER_LITERAL) points += 1
        if (tokenList[1].type == TokenType.ASSIGNATION) points += 1
        return points == 2
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size >= 3
    }

    private fun isNumberExpression(tokenList: List<Token>): Boolean {
        val expression = getExpression(tokenList)
        val formatResult = OperatorIsFormatted().checkSemantic(expression)
        val arithmeticResult = IsArithmeticExpression().checkSyntax(expression)
        return when {
            formatResult && arithmeticResult -> true
            else -> false
        }
    }

    private fun isStringExpression(tokenList: List<Token>): Boolean {
        val expression = getExpression(tokenList)
        return IsStringConcat().checkSyntax(expression)
    }

    private fun createArithmeticNode(tokenList: List<Token>): AstNode {
        return createAssignationAst(
            tokenList,
            ArithmeticNodeBuilder().build(getExpression(tokenList)),
        )
    }

    private fun createStringNode(tokenList: List<Token>): AstNode {
        return createAssignationAst(
            tokenList,
            ContentNodeBuilder().build(getExpression(tokenList)),
        )
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

    private fun getExpression(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size)
    }
}
