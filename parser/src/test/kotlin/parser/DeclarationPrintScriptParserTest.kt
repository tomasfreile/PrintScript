package parser

import ast.BinaryOperationNode
import ast.FunctionNode
import ast.LiteralNode
import ast.NilNode
import ast.VariableDeclarationNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.parserBuilder.printScript11.DeclarationParser11Builder
import position.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DeclarationPrintScriptParserTest {
    private val parser = DeclarationParser11Builder().build()

    @Test
    fun test001_DeclarationParserCanHandleStringDeclaration() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
    }

    @Test
    fun test002_DeclarationParserInvalidNumberDeclarationBecauseOfString() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "age", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidDataTypeException> {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test003_DeclarationParserInvalidStringDeclarationBecauseOfStarOperator() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Malano", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidOperatorException> {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test004_DeclarationParserCanHandleNumberDeclaration() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
    }

    @Test
    fun test005_DeclarationParserCanHandleConst() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.CONST, "const", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
    }

    @Test
    fun test006_DeclarationParserStringConcat() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Malano", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue { parser.canHandle(tokenList) }
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            (node as VariableDeclarationNode).expression is BinaryOperationNode
        }
        assertEquals((((node as VariableDeclarationNode).expression as BinaryOperationNode).left as LiteralNode).value, "Tatiana")
    }

    @Test
    fun test007_DeclarationParserSimpleArithmeticExpression() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            (node as VariableDeclarationNode).expression is BinaryOperationNode
        }
        assertEquals((((node as VariableDeclarationNode).expression as BinaryOperationNode)).operator, TokenType.STAR)
    }

    @Test
    fun test008_DeclarationParserCombinedArithmeticExpression() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            (node as VariableDeclarationNode).expression is BinaryOperationNode
            ((node.expression as BinaryOperationNode)).right is BinaryOperationNode
        }
        assertEquals((((node as VariableDeclarationNode).expression as BinaryOperationNode)).operator, TokenType.PLUS)
        assertEquals(
            (((node.expression as BinaryOperationNode)).right as BinaryOperationNode).operator,
            TokenType.STAR,
        )
    }

    @Test
    fun test009_DeclarationParserArithmeticExpressionWithParen() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.CONST, "const", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            (node as VariableDeclarationNode).expression is BinaryOperationNode
            ((node.expression as BinaryOperationNode)).right is LiteralNode
            ((node.expression as BinaryOperationNode)).left is BinaryOperationNode
        }
        assertEquals((((node as VariableDeclarationNode).expression as BinaryOperationNode)).operator, TokenType.STAR)
        assertEquals(
            (((node.expression as BinaryOperationNode)).left as BinaryOperationNode).operator,
            TokenType.PLUS,
        )
    }

    @Test
    fun test010_DeclarationParserInvalidArithmeticExpressionWithParen() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.CONST, "const", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidOperatorException> {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test011_DeclarationParserArithmeticExpressionWithParenWithTerms() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.CONST, "const", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
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
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
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
    fun test012_DeclarationParserNonAssignedVariable() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            (node as VariableDeclarationNode).expression is NilNode
        }
    }

    @Test
    fun test013_DeclarationParserBooleanLiteralVariable() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "b", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEAN_TYPE, "Boolean", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEAN_LITERAL, "True", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            node as VariableDeclarationNode
            node.expression is LiteralNode
        }
        node as VariableDeclarationNode
        assertEquals((node.expression as LiteralNode).type, TokenType.BOOLEAN_LITERAL)
        assertEquals(node.valueType, TokenType.BOOLEAN_TYPE)
    }

    @Test
    fun test014_DeclarationParserUninitializedBooleanVariable() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.CONST, "const", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "result", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEAN_TYPE, "Boolean", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            (node as VariableDeclarationNode).expression is NilNode
        }
    }

    @Test
    fun test015_DeclarationParserInvalidBooleanDeclarationBecauseOfNumber() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "b", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEAN_TYPE, "Boolean", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEAN_LITERAL, "True", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidDeclarationStatement> {
            parser.createAST(tokenList)
        }
    }

    fun test016_DeclarationParserFunctionNumberDeclaration() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "number", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.READ_INPUT, "readInput", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        val node = parser.createAST(tokenList)
        assertTrue {
            node is VariableDeclarationNode
            node as VariableDeclarationNode
            node.expression is FunctionNode
        }
    }

    fun test017_DeclarationParserFunctionStringInvalidDeclarationBecauseOfNumberTypeDeclaration() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.READ_INPUT, "readInput", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(parser.canHandle(tokenList))
        assertThrows<InvalidDeclarationStatement> {
            parser.createAST(tokenList)
        }
    }
}
