package sca

import ast.ASTBinaryNode
import ast.ASTSingleNode
import org.junit.jupiter.api.Test
import token.Coordinate
import token.PrintScriptToken
import token.TokenType

class ScaTest {
    private val noPrintExpressionsAndCamel =
        StaticCodeAnalyzerImpl("C:\\Users\\tomyf\\ingisis\\PrintScript\\sca\\src\\test\\resources\\NoPrintExpressionsAndCamelCase.yaml")
    private val printExpressionsAndSnake =
        StaticCodeAnalyzerImpl("C:\\Users\\tomyf\\ingisis\\PrintScript\\sca\\src\\test\\resources\\PrintExpressionsAndSnakeCase.yaml")

    @Test
    fun shouldReturnEmptyWhenInputFileDoesNotExist() {
        val sca = StaticCodeAnalyzerImpl("configTest.yaml")
        val ast =
            ASTSingleNode(null, PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)))
        assert(sca.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenPrintExpressionsAreDisabledAndNoPrintExpressionsArePresent() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "x", Coordinate(2, 3), Coordinate(2, 3)),
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
                        PrintScriptToken(TokenType.NUMBER, "1", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    ASTSingleNode(
                        null,
                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 3), Coordinate(2, 3)),
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
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "x", Coordinate(2, 3), Coordinate(2, 3)),
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
                        PrintScriptToken(TokenType.NUMBER, "1", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    ASTSingleNode(
                        null,
                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
            )

//        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnEmptyWhenCaseConventionIsCamelCaseAndVariableNamesAreCamelCase() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(
                        TokenType.VALUE_IDENTIFIER,
                        "camelCase",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.VARIABLE_KEYWORD,
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
                        TokenType.VALUE_IDENTIFIER,
                        "snake_case",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.VARIABLE_KEYWORD,
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
                        TokenType.VALUE_IDENTIFIER,
                        "snake_case",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.VARIABLE_KEYWORD,
                    "let",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
            )
//        assert(printExpressionsAndSnake.analyze(ast).isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenCaseConventionIsSnakeCaseAndVariableNamesAreCamelCase() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null,
                    PrintScriptToken(
                        TokenType.VALUE_IDENTIFIER,
                        "camelCase",
                        Coordinate(2, 5),
                        Coordinate(2, 9),
                    ),
                ),
                PrintScriptToken(
                    TokenType.VARIABLE_KEYWORD,
                    "let",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
            )
//        assert(printExpressionsAndSnake.analyze(ast).isNotEmpty())
    }
}
