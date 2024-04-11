package lexer

import lexer.factory.LexerBuilder
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import position.Coordinate
import token.PrintScriptToken
import token.TokenType

class PrintScriptLexerTest {
    private val lexer = LexerBuilder().build("1.1")

    @Test
    fun simpleExpression() {
        val input = "(2 + 3);"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(0, 0), Coordinate(0, 1)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(0, 1), Coordinate(0, 2)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(0, 3), Coordinate(0, 4)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(0, 5), Coordinate(0, 6)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(0, 6), Coordinate(0, 7)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(0, 7), Coordinate(0, 8)),
            )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun largeExpression() {
        val input = "123 + 2 * 3 - 4 / 5"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.NUMBER_LITERAL, "123", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(0, 4), Coordinate(0, 5)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(0, 6), Coordinate(0, 7)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(0, 8), Coordinate(0, 9)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(0, 10), Coordinate(0, 11)),
                PrintScriptToken(TokenType.MINUS, "-", Coordinate(0, 12), Coordinate(0, 13)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(0, 14), Coordinate(0, 15)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(0, 16), Coordinate(0, 17)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "5", Coordinate(0, 18), Coordinate(0, 19)),
            )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringLiteral() {
        val input = """"Hello, World!""""
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello, World!", Coordinate(0, 1), Coordinate(0, 14)),
            )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringType() {
        val input = "let str: string = 'Hello, World!'"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "str", Coordinate(0, 4), Coordinate(0, 7)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 7), Coordinate(0, 8)),
                PrintScriptToken(TokenType.STRING_TYPE, "string", Coordinate(0, 9), Coordinate(0, 15)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 16), Coordinate(0, 17)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello, World!", Coordinate(0, 19), Coordinate(0, 32)),
            )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun numberType() {
        val input = "let num: number = 123"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "num", Coordinate(0, 4), Coordinate(0, 7)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 7), Coordinate(0, 8)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "number", Coordinate(0, 9), Coordinate(0, 15)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 16), Coordinate(0, 17)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "123", Coordinate(0, 18), Coordinate(0, 21)),
            )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringsWorkWithSimpleQuotes() {
        val input = "let str1: string = 'Hello, World!'"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "str1", Coordinate(0, 4), Coordinate(0, 8)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 8), Coordinate(0, 9)),
                PrintScriptToken(TokenType.STRING_TYPE, "string", Coordinate(0, 10), Coordinate(0, 16)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 17), Coordinate(0, 18)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello, World!", Coordinate(0, 20), Coordinate(0, 33)),
            )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun stringsWorkWithDoubleQuotes() {
        val input = "let str1: string = \"Hello, World!\""
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "str1", Coordinate(0, 4), Coordinate(0, 8)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 8), Coordinate(0, 9)),
                PrintScriptToken(TokenType.STRING_TYPE, "string", Coordinate(0, 10), Coordinate(0, 16)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 17), Coordinate(0, 18)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello, World!", Coordinate(0, 20), Coordinate(0, 33)),
            )

        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun multipleLineInput() {
        val input = "123\nprintln(1) \n let"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.NUMBER_LITERAL, "123", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(1, 0), Coordinate(1, 7)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(1, 7), Coordinate(1, 8)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "1", Coordinate(1, 8), Coordinate(1, 9)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(1, 9), Coordinate(1, 10)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 1), Coordinate(2, 4)),
            )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun letKeywordOnlyWorksIfSurroundedBySpaces() {
        val input = "letx"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "letx", Coordinate(0, 0), Coordinate(0, 4)),
            )
        compareExpectedWithOutput(input, expectedTokens)

        val input2 = "let x"
        val expectedTokens2 =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "x", Coordinate(0, 4), Coordinate(0, 5)),
            )
        compareExpectedWithOutput(input2, expectedTokens2)
    }

    @Test
    fun invalidCharactersThrowExceptionWithPosition() {
        val input = "ha%o"
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            lexer.lex(input)
        }

        val input2 = "let x = 1;&"
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            lexer.lex(input2)
        }
    }

    @Test
    fun ifElse() {
        val input = "if else"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(0, 0), Coordinate(0, 2)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(0, 3), Coordinate(0, 7)),
            )
        compareExpectedWithOutput(input, expectedTokens)
    }

    @Test
    fun booleanDeclaration() {
        val input = "let x: boolean = true"
        val expectedTokens =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(0, 0), Coordinate(0, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "x", Coordinate(0, 4), Coordinate(0, 5)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(0, 5), Coordinate(0, 6)),
                PrintScriptToken(TokenType.BOOLEAN_TYPE, "boolean", Coordinate(0, 7), Coordinate(0, 14)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(0, 15), Coordinate(0, 16)),
                PrintScriptToken(TokenType.BOOLEAN_LITERAL, "true", Coordinate(0, 17), Coordinate(0, 21)),
            )
        compareExpectedWithOutput(input, expectedTokens)
    }

    private fun compareExpectedWithOutput(
        input: String,
        expectedTokens: List<PrintScriptToken>,
    ) {
        val actualTokens = lexer.lex(input)

        Assertions.assertEquals(expectedTokens.size, actualTokens.size)

        for (i in expectedTokens.indices) {
            val expectedToken = expectedTokens[i]
            val actualToken = actualTokens[i]
            if (!expectedToken.equals(actualToken)) {
                println("Expected: ${expectedToken.string()}")
                println("Actual: ${actualToken.string()}")
            }
            Assertions.assertTrue(expectedToken.equals(actualToken))
        }
    }
}
