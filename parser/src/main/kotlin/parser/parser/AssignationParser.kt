package parser.parser

import ast.AssignmentNode
import ast.AstNode
import parser.InvalidSyntaxException
import parser.analysis.semantic.OperatorIsFormatted
import parser.analysis.sintactic.IsArithmeticExpression
import parser.analysis.sintactic.IsBooleanExpression
import parser.analysis.sintactic.IsFunctionExpression
import parser.analysis.sintactic.IsStringExpression
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.BooleanNodeBuilder
import parser.nodeBuilder.FunctionNodeBuilder
import parser.nodeBuilder.StringNodeBuilder
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
            isFunctionExpression(tokenList) -> createFunctionNode(tokenList)
            isNumberExpression(tokenList) -> createArithmeticAst(tokenList)
            isStringExpression(tokenList) -> createStringAst(tokenList)
            isBooleanExpression(tokenList) -> createBooleanAst(tokenList)
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
        return IsStringExpression().checkSyntax(expression)
    }

    private fun isBooleanExpression(tokenList: List<Token>): Boolean {
        val expression = getExpression(tokenList)
        return IsBooleanExpression().checkSyntax(expression)
    }

    private fun isFunctionExpression(tokenList: List<Token>): Boolean {
        val expression = getExpression(tokenList)
        return IsFunctionExpression().checkSyntax(expression)
    }

    private fun createArithmeticAst(tokenList: List<Token>): AstNode {
        return createAssignationAst(
            tokenList,
            ArithmeticNodeBuilder().build(getExpression(tokenList)),
        )
    }

    private fun createStringAst(tokenList: List<Token>): AstNode {
        return createAssignationAst(
            tokenList,
            StringNodeBuilder().build(getExpression(tokenList)),
        )
    }

    private fun createBooleanAst(tokenList: List<Token>): AstNode {
        return createAssignationAst(
            tokenList,
            BooleanNodeBuilder().build(getExpression(tokenList)),
        )
    }

    private fun createFunctionNode(tokenList: List<Token>): AstNode {
        return createAssignationAst(
            tokenList,
            FunctionNodeBuilder().build(getExpression(tokenList)),
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
