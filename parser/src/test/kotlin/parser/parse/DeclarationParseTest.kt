package parser.parse

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import parser.InvalidSyntax
import parser.Parser
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import utils.printAST
import kotlin.test.assertFailsWith

class DeclarationParseTest {
    private val parser: Parser = Parser()

    @Test
    fun test001_ValidSimpleIntDeclaration() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Declaration Parse Test: 001")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test002_ValidSimpleStringDeclaration() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Declaration Parse Test: 002")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test003_ValidArithmeticCombinationDeclaration() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Declaration Parse Test: 003")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test004_ValidStringConcatDeclaration() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "world", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Declaration Parse Test: 004")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test005_InvalidArithmeticDeclarationBecauseOfInvalidOperator() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidSyntax> {
            parser.parse(tokenList)
        }
    }

    @Test
    fun test006_InvalidSimpleStringDeclarationBecauseOfInvalidOperator() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "world", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidSyntax> {
            parser.parse(tokenList)
        }
    }

    @Test
    fun test007_InvalidStringConcatDeclarationBecauseOfInvalidOperator() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "hello", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING, "world", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFailsWith<InvalidSyntax> {
            parser.parse(tokenList)
        }
    }

    @Test
    fun test008_ValidArithmeticDeclarationWithParen() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Declaration Parse Test: 008")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }

    @Test
    fun test009_ValidExtenseArithmeticDeclarationWithParen() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER, "4", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertDoesNotThrow {
            val node = parser.parse(tokenList)
            println("Declaration Parse Test: 009")
            println("==================================")
            printAST(node)
            println("==================================")
        }
    }
}
