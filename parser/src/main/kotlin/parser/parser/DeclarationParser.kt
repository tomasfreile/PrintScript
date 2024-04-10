package parser.parser

import ast.AstNode
import ast.NilNode
import ast.VariableDeclarationNode
import parser.InvalidDeclarationStatement
import parser.InvalidSyntaxException
import parser.analysis.semantic.OperatorIsFormatted
import parser.analysis.sintactic.HasSentenceSeparator
import parser.analysis.sintactic.IsArithmeticExpression
import parser.analysis.sintactic.IsBooleanExpression
import parser.analysis.sintactic.IsFunctionExpression
import parser.analysis.sintactic.IsStringExpression
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.BooleanNodeBuilder
import parser.nodeBuilder.FunctionNodeBuilder
import parser.nodeBuilder.LiteralNodeBuilder
import parser.nodeBuilder.StringNodeBuilder
import token.Token
import token.TokenType

class DeclarationParser(private val separator: TokenType) : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return when {
            preCondition(tokenList) -> isDeclaration(tokenList)
            else -> false
        }
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        return when {
            isNonUninitialized(tokenList) -> createUninitializedDeclarationAst(tokenList)
            isFunctionExpression(tokenList) -> createFunctionDeclarationAst(tokenList)
            isNumberType(tokenList[3]) -> createArithmeticDeclarationAst(tokenList)
            isStringType(tokenList[3]) -> createStringDeclarationAst(tokenList)
            isBooleanType(tokenList[3]) -> createBooleanDeclarationAST(tokenList)
            else -> throw InvalidSyntaxException("Has no type the declaration on line: " + tokenList.first().start.row)
        }
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.size >= 4 &&
            HasSentenceSeparator(separator).checkSyntax(tokenList)
    }

    private fun createBooleanDeclarationAST(tokenList: List<Token>): AstNode {
        val expression = getExpression(tokenList)
        return if (isBooleanExpression(expression)) {
            createDeclarationAst(
                tokenList,
                createBooleanAst(expression),
            )
        } else {
            throw InvalidDeclarationStatement(
                "Invalid Boolean Declaration Statement on line: " + tokenList.first().start.row,
            )
        }
    }

    private fun createUninitializedDeclarationAst(tokenList: List<Token>): AstNode {
        return createDeclarationAst(tokenList)
    }

    private fun createFunctionDeclarationAst(tokenList: List<Token>): AstNode {
        val expression = getExpression(tokenList)
        return if (isFunctionExpressionValid(tokenList)) {
            createDeclarationAst(
                tokenList,
                createFunctionAst(expression),
            )
        } else {
            throw InvalidDeclarationStatement(
                "Invalid readInput Declaration on line: " + tokenList.first().start.row + "    | reason: Invalid input type",
            )
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
                "Invalid Number Declaration Statement on linde: " + tokenList.first().start.row,
            )
        }
    }

    private fun createStringDeclarationAst(tokenList: List<Token>): AstNode {
        val expression = getExpression(tokenList)
        return if (isStringExpression(expression)) {
            createDeclarationAst(
                tokenList,
                if (isLiteral(expression)) createLiteralNode(expression) else createStringAst(expression),
            )
        } else {
            throw InvalidDeclarationStatement(
                "Invalid String Declaration Statement on line: " + tokenList.first().start.row + "\n Is a string declaration dummy",
            )
        }
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
        )
    }

    private fun createStringAst(expression: List<Token>): AstNode {
        return StringNodeBuilder().build(expression)
    }

    private fun createBinaryArithmeticAst(expression: List<Token>): AstNode {
        return ArithmeticNodeBuilder().build(expression)
    }

    private fun createBooleanAst(expression: List<Token>): AstNode {
        return BooleanNodeBuilder().build(expression)
    }

    private fun createFunctionAst(expression: List<Token>): AstNode {
        return FunctionNodeBuilder().build(expression)
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
        return points == 4
    }

    private fun isDeclarativeToken(token: Token): Boolean {
        return when (token.type) {
            TokenType.LET, TokenType.CONST -> true
            else -> false
        }
    }

    private fun isValueType(token: Token): Boolean {
        return isStringType(token) || isNumberType(token) || isBooleanType(token)
    }

    private fun getExpression(tokenList: List<Token>): List<Token> {
        return if (tokenList.size == 5) emptyList() else tokenList.subList(5, tokenList.size - 1) // expression without semicolon
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

    private fun isBooleanType(token: Token): Boolean {
        return when (token.type) {
            TokenType.BOOLEAN_TYPE -> true
            else -> false
        }
    }

    private fun isNonUninitialized(tokenList: List<Token>): Boolean {
        return getExpression(tokenList).isEmpty()
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
        return IsStringExpression().checkSyntax(expression)
    }

    private fun isNumberExpression(expression: List<Token>): Boolean {
        val formatResult = OperatorIsFormatted().checkSemantic(expression)
        val arithmeticResult = IsArithmeticExpression().checkSyntax(expression)
        return when {
            formatResult && arithmeticResult -> true
            else -> false
        }
    }

    private fun isBooleanExpression(expression: List<Token>): Boolean {
        return IsBooleanExpression().checkSyntax(expression)
    }

    private fun isFunctionExpression(tokenList: List<Token>): Boolean {
        val expression = getExpression(tokenList)
        return IsFunctionExpression().checkSyntax(expression)
    }

    private fun isFunctionExpressionValid(tokenList: List<Token>): Boolean {
        val expression = getExpression(tokenList)
        return tokenList[3].type == typeOf(expression[2].type)
    }

    private fun typeOf(tokenType: TokenType): TokenType {
        return when (tokenType) {
            TokenType.NUMBER_LITERAL -> TokenType.NUMBER_TYPE
            TokenType.STRING_LITERAL -> TokenType.STRING_TYPE
            TokenType.BOOLEAN_LITERAL -> TokenType.BOOLEAN_TYPE
            else -> TokenType.NIL
        }
    }
}
