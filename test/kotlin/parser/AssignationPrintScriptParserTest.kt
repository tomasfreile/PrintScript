package parser

import ast.AssignmentNode
import ast.BinaryOperationNode
import ast.FunctionNode
import ast.LiteralNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.parserBuilder.printScript11.AssignationParser11Builder
import position.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AssignationPrintScriptParserTest {
    private val parser = AssignationParser11Builder().build()

    @Test
    fun test001_AssignationParserSimpleStringAssignation() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
        }
        assertEquals((node as AssignmentNode).identifier, "name")
    }

    @Test
    fun test002_AssignationParserSimpleNumberAssignation() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
        }
        assertEquals((node as AssignmentNode).identifier, "three")
        assertEquals((node.expression as LiteralNode).type, TokenType.NUMBERLITERAL)
    }

    @Test
    fun test003_AssignationParserArithmeticExpressionAssignation() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            (node as AssignmentNode).expression is BinaryOperationNode
        }
        assertEquals(((node as AssignmentNode).expression as BinaryOperationNode).operator, TokenType.SLASH)
        assertEquals(((node.expression as BinaryOperationNode).left as LiteralNode).type, TokenType.NUMBERLITERAL)
    }

    @Test
    fun test004_AssignationParserInvalidConcatBecauseOfOperator() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidSyntaxException> {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test005_AssignationParserConcatStringAndNumber() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
            (node as AssignmentNode).expression is BinaryOperationNode
            (node.expression as BinaryOperationNode).left is LiteralNode
        }
        node as AssignmentNode
        assertEquals((node.expression as BinaryOperationNode).operator, TokenType.PLUS)
    }

    @Test
    fun test006_AssignationParsersSimpleArithmeticOperationWithParen() {
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
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
            (node as AssignmentNode).expression is BinaryOperationNode
            (node.expression as BinaryOperationNode).left is BinaryOperationNode
            (node.expression as BinaryOperationNode).right is BinaryOperationNode
        }
        node as AssignmentNode
        assertEquals((node.expression as BinaryOperationNode).operator, TokenType.PLUS)
        assertEquals(((node.expression as BinaryOperationNode).left as BinaryOperationNode).operator, TokenType.PLUS)
        assertEquals(((node.expression as BinaryOperationNode).right as BinaryOperationNode).operator, TokenType.STAR)
        assertEquals(
            (((node.expression as BinaryOperationNode).right as BinaryOperationNode).right as BinaryOperationNode).operator,
            TokenType.PLUS,
        )
    }

    @Test
    fun test007_AssignationParserInvalidArithmeticOperationWithParenBecauseOfString() {
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
                PrintScriptToken(TokenType.STRINGLITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidSyntaxException> {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test008_AssignationParserBooleanLiteral() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "True", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
            node as AssignmentNode
            node.expression is LiteralNode
        }
        assertEquals((node as AssignmentNode).identifier, "condition")
        assertEquals((node.expression as LiteralNode).type, TokenType.BOOLEANLITERAL)
    }

    @Test
    fun test009_AssignationParserInvalidBooleanExpressionBecauseOfNumber() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidSyntaxException> {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test010_AssignationParserReadInputFunctionExpression() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.READINPUT, "readEnv", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Users", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
            node as AssignmentNode
            node.expression is FunctionNode
        }
        node as AssignmentNode
        assertEquals(((node.expression as FunctionNode).expression as LiteralNode).type, TokenType.STRINGLITERAL)
    }
}
