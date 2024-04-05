package parser

import ast.BinaryOperationNode
import ast.LiteralNode
import ast.PrintNode
import org.junit.jupiter.api.Test
import parser.parse.PrintParser
import token.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PrintParseTest {
    private val parse = PrintParser()

    @Test
    fun test001_PrintParseHelloWorld() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello World", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parse.canHandle(tokenList))
        println(parse.createAST(tokenList))
    }

    @Test
    fun test002_PrintParseHelloConcatWithWorld() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "World", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parse.canHandle(tokenList))
        val node = parse.createAST(tokenList)
        assertTrue(node is PrintNode)
        if (node.expression is BinaryOperationNode) {
            assertTrue((node.expression as BinaryOperationNode).left is LiteralNode)
            assertEquals((node.expression as BinaryOperationNode).operator, TokenType.PLUS)
        }
    }

    @Test
    fun test003_PrintParseHelloConcatWithNumberThree() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parse.canHandle(tokenList))
        val node = parse.createAST(tokenList)
        assertTrue(node is PrintNode)
        if (node.expression is BinaryOperationNode) {
            assertTrue((node.expression as BinaryOperationNode).left is LiteralNode)
            assertEquals((node.expression as BinaryOperationNode).operator, TokenType.PLUS)
            assertEquals(((node.expression as BinaryOperationNode).right as LiteralNode).value, "3")
        }
    }

    @Test
    fun test004_PrintParseInvalidConcatBecauseOfOperatorStringStarString() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "World", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidOperatorException> {
            parse.createAST(tokenList)
        }
    }

    @Test
    fun test005_PrintParseInvalidOperator() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidOperatorException> {
            parse.createAST(tokenList)
        }
    }

    @Test
    fun test006_PrintParseStarArithmeticExpression() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(true)
    }
}
