package org.example.test.lexer

import org.example.lexer.PrintScriptLexer
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.TypeEnum
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PrintScriptLexerTest {

    private val lexer = PrintScriptLexer()
    @Test
    fun testSimpleExpression() {

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
    fun testLargeExpression() {
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
    fun testStringLiteral() {
        val input = """"Hello, World!""""
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.STRING, "Hello, World!", Coordinate(0, 0), Coordinate(0, 15)))

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun testStringAndNumberType(){
        val input = "let str: String = 'Hello, World!' let num: Number = 123"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "str", Coordinate(0, 4), Coordinate(0, 7)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 8), Coordinate(0, 9)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(0, 10), Coordinate(0, 16)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 17), Coordinate(0, 18)),
            PrintScriptToken(TypeEnum.STRING, "Hello, World!", Coordinate(0, 19), Coordinate(0, 34)),
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 35), Coordinate(0, 38)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "num", Coordinate(0, 39), Coordinate(0, 42)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 43), Coordinate(0, 44)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Number", Coordinate(0, 45), Coordinate(0, 51)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 52), Coordinate(0, 53)),
            PrintScriptToken(TypeEnum.NUMBER, "123", Coordinate(0, 54), Coordinate(0, 57))
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun testIllegalCharacterThrowsException() {
        val input = "let num: Number = 123#"
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            lexer.lex(input)
        }
    }

    @Test
    fun testStringsWorkWithSimpleAndDoubleQuotes() {
        val input = "let str1: String = 'Hello, World!' let str2: String = \"Hello, World!\""
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "str1", Coordinate(0, 4), Coordinate(0, 8)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 9), Coordinate(0, 10)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(0, 11), Coordinate(0, 17)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 18), Coordinate(0, 19)),
            PrintScriptToken(TypeEnum.STRING, "Hello, World!", Coordinate(0, 20), Coordinate(0, 35)),
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 36), Coordinate(0, 39)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "str2", Coordinate(0, 40), Coordinate(0, 44)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(0, 45), Coordinate(0, 46)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(0, 47), Coordinate(0, 53)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(0, 54), Coordinate(0, 55)),
            PrintScriptToken(TypeEnum.STRING, "Hello, World!", Coordinate(0, 56), Coordinate(0, 71))
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun testMultipleLineInput(){
        val input = "let\n num: Number = 123 \n println(num)"
        val expectedTokens = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "num", Coordinate(1, 1), Coordinate(1, 4)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(1, 5), Coordinate(1, 6)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Number", Coordinate(1, 7), Coordinate(1, 13)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(1, 14), Coordinate(1, 15)),
            PrintScriptToken(TypeEnum.NUMBER, "123", Coordinate(1, 16), Coordinate(1, 19)),
            PrintScriptToken(TypeEnum.PRINT, "println", Coordinate(2, 1), Coordinate(2, 8)),
            PrintScriptToken(TypeEnum.LEFT_PAREN, "(", Coordinate(2, 8), Coordinate(2, 9)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "num", Coordinate(2, 9), Coordinate(2, 12)),
            PrintScriptToken(TypeEnum.RIGHT_PAREN, ")", Coordinate(2, 12), Coordinate(2, 13))
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
            Assertions.assertEquals(expectedTokens[i].type, actualTokens[i].type)
            Assertions.assertEquals(expectedTokens[i].value, actualTokens[i].value)
        }
    }


}