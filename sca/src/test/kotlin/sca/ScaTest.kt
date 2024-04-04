package sca

import ast.ASTBinaryNode
import ast.ASTSingleNode
import org.junit.jupiter.api.Test
import token.Coordinate
import token.PrintScriptToken
import token.TokenType

class ScaTest {
    // use resources files
    private val noPrintExpressionsAndCamel =
        StaticCodeAnalyzerImpl("src/test/resources/NoPrintExpressionsAndCamelCase.yaml")

    private val printExpressionsAndSnake =
        StaticCodeAnalyzerImpl("src/test/resources/PrintExpressionsAndSnakeCase.yaml")

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreDisabledAndNoPrintExpressionsArePresent() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "x", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenPrintExpressionsAreDisabledAndPrintExpressionsArePresent() {
        val ast =
            ASTSingleNode(
                ASTBinaryNode(
                    ASTSingleNode(
                        null,
                        PrintScriptToken(TokenType.NUMBER_LITERAL, "1", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    ASTSingleNode(
                        null,
                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
            )

        assert(noPrintExpressionsAndCamel.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreEnabledAndNoPrintExpressionsArePresent() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "x", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreEnabledAndPrintExpressionsArePresent() {
        val ast =
            ASTSingleNode(
                ASTBinaryNode(
                    ASTSingleNode(
                        null,
                        PrintScriptToken(TokenType.NUMBER_LITERAL, "1", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    ASTSingleNode(
                        null,
                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
            )

        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenCaseConventionIsCamelCaseAndVariableNamesAreCamelCase() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(
                        TokenType.VALUE_IDENTIFIER_LITERAL,
                        "camelCase",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.LET,
                    "let",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenCaseConventionIsCamelCaseAndVariableNamesAreSnakeCase() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(
                        TokenType.VALUE_IDENTIFIER_LITERAL,
                        "snake_case",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.LET,
                    "let",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
            )
        assert(noPrintExpressionsAndCamel.analyze(ast).isNotEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenCaseConventionIsSnakeCaseAndVariableNamesAreSnakeCase() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(
                        TokenType.VALUE_IDENTIFIER_LITERAL,
                        "snake_case",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.LET,
                    "let",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
            )
        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenCaseConventionIsSnakeCaseAndVariableNamesAreCamelCase() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(
                        TokenType.VALUE_IDENTIFIER_LITERAL,
                        "camelCase",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.LET,
                    "let",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
            )
        assert(printExpressionsAndSnake.analyze(ast).isNotEmpty())
    }
}
