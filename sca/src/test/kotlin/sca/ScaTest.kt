@file:Suppress("ktlint:standard:no-wildcard-imports")

package sca

import ast.*
import org.junit.jupiter.api.Test
import position.Coordinate
import position.TokenPosition
import token.TokenType

class ScaTest {
    private val noPrintExpressionsAndCamel =
        StaticCodeAnalyzerImpl("src/test/resources/NoPrintExpressionsAndCamelCase.yaml", "1.0")

    private val printExpressionsAndSnake =
        StaticCodeAnalyzerImpl("src/test/resources/PrintExpressionsAndSnakeCase.yaml", "1.0")

    private val noReadInputExpressions =
        StaticCodeAnalyzerImpl("src/test/resources/NoReadInputExpressions.yaml", "1.1")

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreDisabledAndNoPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenPrintExpressionsAreDisabledAndPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                BinaryOperationNode(
                    LiteralNode("1", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 5), Coordinate(0, 10)),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreEnabledAndNoPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreEnabledAndPrintExpressionsArePresent() {
        val ast =
            PrintNode(
                BinaryOperationNode(
                    LiteralNode(
                        "1",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    LiteralNode(
                        "hola",
                        TokenType.STRINGLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenCaseConventionIsCamelCaseAndVariableNamesAreCamelCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "camelCase",
                TokenType.NUMBERTYPE,
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 1), Coordinate(0, 6)),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenCaseConventionIsCamelCaseAndVariableNamesAreSnakeCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "snake_case",
                TokenType.NUMBERTYPE,
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 1), Coordinate(0, 5)),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenCaseConventionIsSnakeCaseAndVariableNamesAreSnakeCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "snake_case",
                TokenType.NUMBERTYPE,
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenCaseConventionIsSnakeCaseAndVariableNamesAreCamelCase() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "camelCase",
                TokenType.NUMBERTYPE,
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(printExpressionsAndSnake.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenReadInputExpressionsAreDisabledAndNoReadInputExpressionsArePresent() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "variable",
                TokenType.NUMBERTYPE,
                FunctionNode(
                    TokenType.READINPUT,
                    LiteralNode(
                        "1",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(noReadInputExpressions.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenReadInputExpressionsAreDisabledAndReadInputExpressionsArePresentInDeclaration() {
        val ast =
            VariableDeclarationNode(
                TokenType.LET,
                "variable",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    FunctionNode(
                        TokenType.READINPUT,
                        LiteralNode(
                            "1",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(noReadInputExpressions.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnWarningWhenReadInputExpressionsAreDisabledAndReadInputExpressionsArePresentInAssignment() {
        val ast =
            AssignmentNode(
                "input",
                BinaryOperationNode(
                    FunctionNode(
                        TokenType.READINPUT,
                        LiteralNode(
                            "1",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.NUMBERTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assert(noReadInputExpressions.analyze(ast).isNotEmpty())
    }
}
