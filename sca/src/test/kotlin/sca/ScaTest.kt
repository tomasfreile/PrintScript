package sca

import ast.BinaryOperationNode
import ast.FunctionNode
import ast.LiteralNode
import ast.PrintNode
import ast.VariableDeclarationNode
import org.junit.jupiter.api.Test
import token.TokenType

class ScaTest {
    private val noPrintExpressionsAndCamel =
        StaticCodeAnalyzerImpl("src/test/resources/NoPrintExpressionsAndCamelCase.yaml")

    private val printExpressionsAndSnake =
        StaticCodeAnalyzerImpl("src/test/resources/PrintExpressionsAndSnakeCase.yaml")

    private val noReadInputExpressions =
        StaticCodeAnalyzerImpl("src/test/resources/NoReadInputExpressions.yaml")

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreDisabledAndNoPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                LiteralNode("1", TokenType.NUMBER_LITERAL),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenPrintExpressionsAreDisabledAndPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                BinaryOperationNode(
                    LiteralNode("1", TokenType.NUMBER_LITERAL),
                    LiteralNode("2", TokenType.NUMBER_LITERAL),
                    TokenType.PLUS,
                ),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreEnabledAndNoPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                LiteralNode("1", TokenType.NUMBER_LITERAL),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreEnabledAndPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                BinaryOperationNode(
                    LiteralNode("1", TokenType.NUMBER_LITERAL),
                    LiteralNode("hola", TokenType.STRING_LITERAL),
                    TokenType.PLUS,
                ),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenCaseConventionIsCamelCaseAndVariableNamesAreCamelCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "camelCase",
                TokenType.NUMBER_TYPE,
                LiteralNode("1", TokenType.NUMBER_LITERAL),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenCaseConventionIsCamelCaseAndVariableNamesAreSnakeCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "snake_case",
                TokenType.NUMBER_TYPE,
                LiteralNode("1", TokenType.NUMBER_LITERAL),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenCaseConventionIsSnakeCaseAndVariableNamesAreSnakeCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "snake_case",
                TokenType.NUMBER_TYPE,
                LiteralNode("1", TokenType.NUMBER_LITERAL),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenCaseConventionIsSnakeCaseAndVariableNamesAreCamelCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "camelCase",
                TokenType.NUMBER_TYPE,
                LiteralNode("1", TokenType.NUMBER_LITERAL),
            )
        assert(printExpressionsAndSnake.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenReadInputExpressionsAreDisabledAndNoReadInputExpressionsArePresent() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "variable",
                TokenType.NUMBER_TYPE,
                LiteralNode("1", TokenType.NUMBER_LITERAL),
            )
        assert(noReadInputExpressions.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenReadInputExpressionsAreDisabledAndReadInputExpressionsArePresent() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "variable",
                TokenType.NUMBER_TYPE,
                FunctionNode(
                    TokenType.READ_INPUT,
                    BinaryOperationNode(
                        LiteralNode("1", TokenType.NUMBER_LITERAL),
                        LiteralNode("2", TokenType.NUMBER_LITERAL),
                        TokenType.PLUS,
                    ),
                ),
            )
        assert(noReadInputExpressions.analyze(ast).isNotEmpty())
    }
}
