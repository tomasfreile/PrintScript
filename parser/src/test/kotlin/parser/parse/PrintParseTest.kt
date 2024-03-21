package parser.parse

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import parser.InvalidSyntax
import parser.Parser
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType

class PrintParseTest {

    val parser = Parser()
    @Test
    fun parseSimplePrintValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }

    @Test
    fun parseBinaryPrintValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Adios", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }

    @Test
    fun parseExtenseBinaryPrintValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Adios", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "hola", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "otro adiós", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }

    @Test
    fun parseNumbrePrintValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "8", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }

    @Test
    fun parseBinaryStringPrintInvalidOperatorTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SLASH, "/", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Adios", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(false)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(true)
        }
    }

    @Test
    fun parseSimpleStringPrintInvalidSyntaxTEst(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Adios", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(false)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(true)
        }
    }

    @Test
    fun parseArithmeticCombinationValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "8", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }

    @Test
    fun parseStarArithmeticCombinationValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "8", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STAR, "*", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "5", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        try{
            val node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }
}