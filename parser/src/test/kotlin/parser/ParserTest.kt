package parser

import ast.Node
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType

class ParserTest {
    @Test
    fun stringDeclarationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun binaryDeclarationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "sum", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun tripleSumDeclarationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "sum", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun binaryProductTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "product", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun binaryDivisionTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "product", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun binarySubstractTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "product", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun plusComesFirstThanMinusTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "sum", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun minusComeFistThanPlusTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "sum", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun numberPlusProductTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "combination", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun productPlusPrudctTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "combination", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun parenSimplePlusProductOperationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "combination", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }

    @Test
    fun doubleParenSimplePlusProductOperationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "combination", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "8", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "9", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TokenType.VARIABLE_KEYWORD)
    }
}
