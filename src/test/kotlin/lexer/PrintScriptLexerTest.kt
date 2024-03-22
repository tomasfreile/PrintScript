package org.example.test.lexer

import org.example.lexer.PrintScriptLexer
import org.example.lexer.getTokenMap
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.TokenType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PrintScriptLexerTest {

    private val lexer = PrintScriptLexer(getTokenMap())
    @Test
    fun testSimpleExpression() {

        val input = "(2 + 3);"
        val expectedTokens = listOf(
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(0, 0), Coordinate(0, 1)),
            PrintScriptToken(TokenType.NUMBER, "2", Coordinate(0, 1), Coordinate(0, 2)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(0, 3), Coordinate(0, 4)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(0, 5), Coordinate(0, 6)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(0, 6), Coordinate(0, 7)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(0, 7), Coordinate(0, 8))
        )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun testLargeExpression() {
        val input = "123 + 2 * 3 - 4 / 5"
        val expectedTokens = listOf(
            PrintScriptToken(TokenType.NUMBER, "123", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(0, 4), Coordinate(0, 5)),
            PrintScriptToken(TokenType.NUMBER, "2", Coordinate(0, 6), Coordinate(0, 7)),
            PrintScriptToken(TokenType.STAR, "*", Coordinate(0, 8), Coordinate(0, 9)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(0, 10), Coordinate(0, 11)),
            PrintScriptToken(TokenType.MINUS, "-", Coordinate(0, 12), Coordinate(0, 13)),
            PrintScriptToken(TokenType.NUMBER, "4", Coordinate(0, 14), Coordinate(0, 15)),
            PrintScriptToken(TokenType.SLASH, "/", Coordinate(0, 16), Coordinate(0, 17)),
            PrintScriptToken(TokenType.NUMBER, "5", Coordinate(0, 18), Coordinate(0, 19))
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun testStringLiteral() {
        val input = """"Hello, World!""""
        val expectedTokens = listOf(
            PrintScriptToken(TokenType.STRING, "\"Hello, World!\"", Coordinate(0, 0), Coordinate(0, 15))
        )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun testStringAndNumberType(){
        val input = "let str: String = 'Hello, World!' let num: Number = 123"
        val expectedTokens = listOf(
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "str", Coordinate(0, 4), Coordinate(0, 7)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 7), Coordinate(0, 8)),
            PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(0, 9), Coordinate(0, 15)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 16), Coordinate(0, 17)),
            PrintScriptToken(TokenType.STRING, "'Hello, World!'", Coordinate(0, 18), Coordinate(0, 33)),
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(0, 34), Coordinate(0, 37)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "num", Coordinate(0, 38), Coordinate(0, 41)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 41), Coordinate(0, 42)),
            PrintScriptToken(TokenType.NUMBER_TYPE, "Number", Coordinate(0, 43), Coordinate(0, 49)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 50), Coordinate(0, 51)),
            PrintScriptToken(TokenType.NUMBER, "123", Coordinate(0, 52), Coordinate(0, 55))
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

//    @Test
//    fun testIllegalCharacterThrowsException() {
//        val input = "le%t num: Number = 123#"
//        Assertions.assertThrows(IllegalArgumentException::class.java) {
//            lexer.lex(input)
//        }
//    }

    @Test
    fun testStringsWorkWithSimpleAndDoubleQuotes() {
        val input = "let str1: String = 'Hello, World!' let str2: String = \"Hello, World!\""
        val expectedTokens = listOf(
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "str1", Coordinate(0, 4), Coordinate(0, 8)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 8), Coordinate(0, 9)),
            PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(0, 10), Coordinate(0, 16)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 17), Coordinate(0, 18)),
            PrintScriptToken(TokenType.STRING, "'Hello, World!'", Coordinate(0, 19), Coordinate(0, 34)),
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(0, 35), Coordinate(0, 38)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "str2", Coordinate(0, 39), Coordinate(0, 43)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 43), Coordinate(0, 44)),
            PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(0, 45), Coordinate(0, 51)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 52), Coordinate(0, 53)),
            PrintScriptToken(TokenType.STRING, "\"Hello, World!\"", Coordinate(0, 54), Coordinate(0, 69))
        )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun testMultipleLineInput(){
        val input = "let num: Number = 123\nprintln(num) \n println(num)"
        val expectedTokens = listOf(
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(0, 0), Coordinate(0, 3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "num", Coordinate(0, 4), Coordinate(0, 7)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 7), Coordinate(0, 8)),
            PrintScriptToken(TokenType.NUMBER_TYPE, "Number", Coordinate(0, 9), Coordinate(0, 15)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 16), Coordinate(0, 17)),
            PrintScriptToken(TokenType.NUMBER, "123", Coordinate(0, 18), Coordinate(0, 21)),
            PrintScriptToken(TokenType.PRINT, "println", Coordinate(1, 0), Coordinate(1, 7)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(1, 7), Coordinate(1, 8)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "num", Coordinate(1, 8), Coordinate(1, 11)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(1, 11), Coordinate(1, 12)),
            PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 1), Coordinate(2, 8)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 8), Coordinate(2, 9)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "num", Coordinate(2, 9), Coordinate(2, 12)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 12), Coordinate(2, 13))
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
            Assertions.assertEquals(expectedToken.type, actualToken.type)
            Assertions.assertEquals(expectedToken.value, actualToken.value)
            Assertions.assertEquals(expectedToken.start.row, actualToken.start.row)
            Assertions.assertEquals(expectedToken.start.column, actualToken.start.column)
            Assertions.assertEquals(expectedToken.end.row, actualToken.end.row)
            Assertions.assertEquals(expectedToken.end.column, actualToken.end.column)
        }
    }


}