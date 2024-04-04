package parser.parse

import org.junit.jupiter.api.assertDoesNotThrow
import parser.InvalidSyntax
import parser.Parser
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import utils.printAST
import kotlin.test.Test
import kotlin.test.assertFailsWith

class AssignationParseTest {
    private val parser: Parser = Parser()

    @Test
    fun test001_ParseSimpleValidAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Assignation Parse Test: 001")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test002_ParseCombinationValidAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Assignation Parse Test: 002")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test003_ParseInvalidCombinationAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidSyntax> {
            parser.parse(tokenList)
        }
    }

    @Test
    fun test004_ParseConcatenationAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, " holas", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Assignation Parse Test: 003")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test005_ParseInvalidConcatenationAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, " holas", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidSyntax> {
            parser.parse(tokenList)
        }
    }

    @Test
    fun test006_ParseValidParenthesisTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Assignation Parse Test: 004")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test007_ParseInvalidParenTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidSyntax> {
            parser.parse(tokenList)
        }
    }

    @Test
    fun test008_ParseExtenseValidParenthesisTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Assignation Parse Test: 005")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test009_ParseExtenseInvalidParenthesisTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidSyntax> {
            parser.parse(tokenList)
        }
    }
}
