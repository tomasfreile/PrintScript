@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser

import ast.*
import org.junit.jupiter.api.Test
import parser.parserBuilder.PrintScriptOnePointZeroParserBuilder
import position.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PrintScriptParserTest {
    private val parser = PrintScriptOnePointZeroParserBuilder().build()

    @Test
    fun test001_DeclarationParser() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERTYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "10", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "2", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            (node as VariableDeclarationNode).expression is BinaryOperationNode
            (node.expression as BinaryOperationNode).left is BinaryOperationNode
            (node.expression as BinaryOperationNode).right is BinaryOperationNode
        }
        assertEquals(((node as VariableDeclarationNode).expression as BinaryOperationNode).operator, TokenType.PLUS)
        assertEquals(((node.expression as BinaryOperationNode).left as BinaryOperationNode).operator, TokenType.PLUS)
        assertEquals(((node.expression as BinaryOperationNode).right as BinaryOperationNode).operator, TokenType.STAR)
        assertEquals(
            (((node.expression as BinaryOperationNode).right as BinaryOperationNode).right as BinaryOperationNode).operator,
            TokenType.PLUS,
        )
    }

    @Test
    fun test002_AssignationParser() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "10", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "2", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
            node as AssignmentNode
            (node.expression as BinaryOperationNode).left is BinaryOperationNode
            (node.expression as BinaryOperationNode).right is BinaryOperationNode
        }
        assertEquals(((node as AssignmentNode).expression as BinaryOperationNode).operator, TokenType.PLUS)
        assertEquals(((node.expression as BinaryOperationNode).left as BinaryOperationNode).operator, TokenType.PLUS)
        assertEquals(((node.expression as BinaryOperationNode).right as BinaryOperationNode).operator, TokenType.STAR)
        assertEquals(
            (((node.expression as BinaryOperationNode).right as BinaryOperationNode).right as BinaryOperationNode).operator,
            TokenType.PLUS,
        )
    }

    @Test
    fun test003_PrintParser() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "8", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "6", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val node = parser.createAST(tokenList)
        println(node)
        if ((node as PrintNode).expression is BinaryOperationNode) {
            assertTrue((node.expression as BinaryOperationNode).left is BinaryOperationNode)
            assertTrue(((node.expression as BinaryOperationNode).left as BinaryOperationNode).left is LiteralNode)
            assertEquals((node.expression as BinaryOperationNode).operator, TokenType.PLUS)
            assertEquals(((node.expression as BinaryOperationNode).left as BinaryOperationNode).operator, TokenType.STAR)
        }
    }
}
