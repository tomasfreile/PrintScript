package org.example.test.lexer

import org.example.lexer.PrintScriptLexer
import org.example.lexer.getTokenMap
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.TypeEnum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class PrintScriptLexerTest {

    private val lexer = PrintScriptLexer(getTokenMap())
    @Test
    fun simpleExpression() {

        val input = "(2 + 3);"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.LEFT_PAREN, "(", Coordinate(0, 0), Coordinate(0, 1)),
            PrintScriptToken(TypeEnum.NUMBER, "2", Coordinate(0, 1), Coordinate(0, 2)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(0, 3), Coordinate(0, 4)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(0, 5), Coordinate(0, 6)),
            PrintScriptToken(TypeEnum.RIGHT_PAREN, ")", Coordinate(0, 6), Coordinate(0, 7)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(0, 7), Coordinate(0, 8))
        )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun largeExpression() {
        val input = "123 + 2 * 3 - 4 / 5"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.NUMBER, "123", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(0, 4), Coordinate(0, 5)),
            PrintScriptToken(TypeEnum.NUMBER, "2", Coordinate(0, 6), Coordinate(0, 7)),
            PrintScriptToken(TypeEnum.STAR, "*", Coordinate(0, 8), Coordinate(0, 9)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(0, 10), Coordinate(0, 11)),
            PrintScriptToken(TypeEnum.MINUS, "-", Coordinate(0, 12), Coordinate(0, 13)),
            PrintScriptToken(TypeEnum.NUMBER, "4", Coordinate(0, 14), Coordinate(0, 15)),
            PrintScriptToken(TypeEnum.SLASH, "/", Coordinate(0, 16), Coordinate(0, 17)),
            PrintScriptToken(TypeEnum.NUMBER, "5", Coordinate(0, 18), Coordinate(0, 19))
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringLiteral() {
        val input = """"Hello, World!""""
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.STRING, "\"Hello, World!\"", Coordinate(0, 0), Coordinate(0, 15))
        )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringType(){
        val input = "let str: String = 'Hello, World!'"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "str", Coordinate(0, 4), Coordinate(0, 7)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 7), Coordinate(0, 8)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(0, 9), Coordinate(0, 15)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 16), Coordinate(0, 17)),
            PrintScriptToken(TypeEnum.STRING, "'Hello, World!'", Coordinate(0, 18), Coordinate(0, 33)),
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun numberType(){
        val input = "let num: Number = 123"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "num", Coordinate(0, 4), Coordinate(0, 7)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 7), Coordinate(0, 8)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Number", Coordinate(0, 9), Coordinate(0, 15)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 16), Coordinate(0, 17)),
            PrintScriptToken(TypeEnum.NUMBER, "123", Coordinate(0, 18), Coordinate(0, 21))
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringsWorkWithSimpleQuotes() {
        val input = "let str1: String = 'Hello, World!'"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "str1", Coordinate(0, 4), Coordinate(0, 8)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 8), Coordinate(0, 9)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(0, 10), Coordinate(0, 16)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 17), Coordinate(0, 18)),
            PrintScriptToken(TypeEnum.STRING, "'Hello, World!'", Coordinate(0, 19), Coordinate(0, 34)),
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringsWorkWithDoubleQuotes() {
        val input = "let str1: String = \"Hello, World!\""
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "str1", Coordinate(0, 4), Coordinate(0, 8)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 8), Coordinate(0, 9)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(0, 10), Coordinate(0, 16)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 17), Coordinate(0, 18)),
            PrintScriptToken(TypeEnum.STRING, "\"Hello, World!\"", Coordinate(0, 19), Coordinate(0, 34)),
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun multipleLineInput(){
        val input = "123\nprintln(1) \n let"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.NUMBER, "123", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.PRINT, "println", Coordinate(1, 0), Coordinate(1, 7)),
            PrintScriptToken(TypeEnum.LEFT_PAREN, "(", Coordinate(1, 7), Coordinate(1, 8)),
            PrintScriptToken(TypeEnum.NUMBER, "1", Coordinate(1, 8), Coordinate(1, 9)),
            PrintScriptToken(TypeEnum.RIGHT_PAREN, ")", Coordinate(1, 9), Coordinate(1, 10)),
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2, 1), Coordinate(2, 4))
        )
        compareExpectedWithOutput(input, expectedTokens)
    }

    private fun compareExpectedWithOutput(
        input: String,
        expectedTokens: List<PrintScriptToken>
    ) {
        val actualTokens = lexer.lex(input)

        Assertions.assertEquals(expectedTokens.size, actualTokens.size)

        for (i in expectedTokens.indices) {
            val expectedToken = expectedTokens[i]
            val actualToken = actualTokens[i]
            Assertions.assertTrue(expectedToken.equals(actualToken))
        }
    }

}