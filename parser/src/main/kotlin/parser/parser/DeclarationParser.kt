package parser.parser

import ast.AstNode
import ast.VariableDeclarationNode
import parser.InvalidDeclarationStatement
import parser.InvalidOperatorException
import parser.InvalidSyntaxException
import parser.analysis.semantic.OperatorIsFormatted
import parser.analysis.sintactic.IsArithmeticExpression
import parser.analysis.sintactic.IsStringConcat
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.ContentNodeBuilder
import parser.nodeBuilder.LiteralNodeBuilder
import token.Token
import token.TokenType

class DeclarationParser : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            hasEnoughLength(tokenList) -> isDeclaration(tokenList)
            else -> false
        }
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        return when {
            isNumberType(tokenList[3]) -> createArithmeticDeclarationAst(tokenList)
            isStringType(tokenList[3]) -> createStringDeclarationAst(tokenList)
            else -> throw InvalidSyntaxException("Has no type the declaration on line: " + tokenList.first().start.row)
        }
    }

    private fun createArithmeticDeclarationAst(tokenList: List<Token>): AstNode {
        val expression = getExpression(tokenList)
        return if (isNumberExpression(expression)) {
            createDeclarationAst(
                tokenList,
                if (isLiteral(expression)) createLiteralNode(expression) else createBinaryArithmeticAst(expression),
            )
        } else {
            throw InvalidDeclarationStatement(
                "Invalid Declaration Statement on linde: " + tokenList.first().start.row + "\n Is a number declaration dummy",
            )
        }
    }

    private fun createStringDeclarationAst(tokenList: List<Token>): AstNode {
        val expression = getExpression(tokenList)
        try {
            return if (isStringExpression(expression)) {
                createDeclarationAst(
                    tokenList,
                    if (isLiteral(expression)) createLiteralNode(expression) else createStringAst(expression),
                )
            } else {
                throw InvalidDeclarationStatement(
                    "Invalid Declaration Statement on line: " + tokenList.first().start.row + "\n Is a string declaration dummy",
                )
            }
        } catch (e: InvalidOperatorException) {
            throw InvalidDeclarationStatement(
                "Invalid Declaration Statement on linde: " + tokenList.first().start.row + "\n Is a string declaration dummy",
            )
        }
    }

    private fun createDeclarationAst(
        tokenList: List<Token>,
        node: AstNode,
    ): AstNode {
        return VariableDeclarationNode(
            tokenList[0].type,
            tokenList[1].value,
            tokenList[3].type,
            node,
        )
    }

    private fun createStringAst(expression: List<Token>): AstNode {
        return ContentNodeBuilder().build(expression)
    }

    private fun createBinaryArithmeticAst(expression: List<Token>): AstNode {
        return ArithmeticNodeBuilder().build(expression)
    }

    private fun createLiteralNode(tokenList: List<Token>): AstNode {
        return LiteralNodeBuilder().build(tokenList)
    }

    private fun isDeclaration(tokenList: List<Token>): Boolean {
        var points = 0
        if (isDeclarativeToken(tokenList[0])) points += 1
        if (tokenList[1].type == TokenType.VALUE_IDENTIFIER_LITERAL) points += 1
        if (tokenList[2].type == TokenType.COLON) points += 1
        if (isValueType(tokenList[3])) points += 1
        if (tokenList[4].type == TokenType.ASSIGNATION) points += 1
        return points == 5
    }

    private fun hasEnoughLength(tokenList: List<Token>): Boolean {
        return tokenList.size >= 6 // at least has the structure and content
    }

    private fun isDeclarativeToken(token: Token): Boolean {
        return when (token.type) {
            TokenType.LET, TokenType.CONST -> true
            else -> false
        }
    }

    private fun isValueType(token: Token): Boolean {
        return isStringType(token) || isNumberType(token)
    }

    private fun getExpression(tokenList: List<Token>): List<Token> {
        return tokenList.subList(5, tokenList.size)
    }

    private fun isStringType(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING_TYPE -> true
            else -> false
        }
    }

    private fun isNumberType(token: Token): Boolean {
        return when (token.type) {
            TokenType.NUMBER_TYPE -> true
            else -> false
        }
    }

    private fun isLiteral(tokenList: List<Token>): Boolean {
        if (tokenList.size == 1) {
            return when (tokenList.first().type) {
                TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL, TokenType.BOOLEAN_LITERAL -> true
                else -> false
            }
        }
        return false
    }

    private fun isStringExpression(expression: List<Token>): Boolean {
        return IsStringConcat().checkSyntax(expression)
    }

    private fun isNumberExpression(expression: List<Token>): Boolean {
        val formatResult = OperatorIsFormatted().checkSemantic(expression)
        val arithmeticResult = IsArithmeticExpression().checkSyntax(expression)
        return when {
            formatResult && arithmeticResult -> true
            else -> false
        }
    }
}
