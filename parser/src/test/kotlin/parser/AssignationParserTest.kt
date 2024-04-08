package parser

import ast.AssignmentNode
import ast.BinaryOperationNode
import ast.FunctionNode
import ast.LiteralNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.parser.AssignationParser
import token.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AssignationParserTest {
    private val parser = AssignationParser()

    @Test
    fun test001_AssignationParserSimpleStringAssignation() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
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
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
        }
        assertEquals((node as AssignmentNode).identifier, "three")
        assertEquals((node.expression as LiteralNode).type, TokenType.NUMBER_LITERAL)
    }

    @Test
    fun test003_AssignationParserArithmeticExpressionAssignation() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            (node as AssignmentNode).expression is BinaryOperationNode
        }
        assertEquals(((node as AssignmentNode).expression as BinaryOperationNode).operator, TokenType.SLASH)
        assertEquals(((node.expression as BinaryOperationNode).left as LiteralNode).type, TokenType.NUMBER_LITERAL)
    }

    @Test
    fun test004_AssignationParserInvalidConcatBecauseOfOperator() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidOperatorException> {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test005_AssignationParserConcatStringAndNumber() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello", Coordinate(2, 3), Coordinate(2, 3)),
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
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "10", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
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
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "10", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
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
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEAN_LITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
            node as AssignmentNode
            node.expression is LiteralNode
        }
        assertEquals((node as AssignmentNode).identifier, "condition")
        assertEquals((node.expression as LiteralNode).type, TokenType.BOOLEAN_LITERAL)
    }

    @Test
    fun test009_AssignationParserInvalidBooleanExpressionBecauseOfNumber() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEAN_LITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
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
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.READ_INPUT, "readInput", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is AssignmentNode
            node as AssignmentNode
            node.expression is FunctionNode
        }
        node as AssignmentNode
        assertEquals(((node.expression as FunctionNode).expression as LiteralNode).type, TokenType.STRING_LITERAL)
    }
}
