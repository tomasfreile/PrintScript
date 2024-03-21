package parser

import parser.sintactic.isAssignation
import org.example.parser.sintactic.declarative.isDeclarative
import parser.sintactic.commons.hasCombination
import parser.sintactic.isPrint
import org.junit.jupiter.api.Test
import parser.sintactic.commons.HasInvalidOperator
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParserSyntaxTest {

    @Test
    fun isDeclarativeValidTEst(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(isDeclarative().checkSyntax(tokenList))
    }

    @Test
    fun isDeclarativeInvalidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(isDeclarative().checkSyntax(tokenList))
    }

    @Test
    fun isPrintValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(isPrint().checkSyntax(tokenList))
    }

    @Test
    fun isPrintInvalidLengthTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(isPrint().checkSyntax(tokenList))
    }

    @Test
    fun isPrintInvalidTokenTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(isPrint().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombinationJustOneStringTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombinationBinaryTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Cómo estas?", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombinationWithInvalidOperatorTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STAR, "*", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Cómo estas?", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombiantionWithStringAndNumber(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasCombinationWithInvalidIdentifier(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "Hola, Mundo", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasNumberCombinationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SLASH, "/", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasNumberCombinationWithIdentifierTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SLASH, "/", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidCombinationWithStrangeOperatorTEst(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SLASH, "/", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SLASH, "/", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidStringCombinationWithStrangeOperatorTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SLASH, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SLASH, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun isAssignationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(isAssignation().checkSyntax(tokenList))
    }

    @Test
    fun invalidLentghForAssignationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.PRINT, "print", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(isAssignation().checkSyntax(tokenList))
    }

    @Test
    fun invalidAssignationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(isAssignation().checkSyntax(tokenList))
    }

    @Test
    fun validAssignationCombinatedTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(isAssignation().checkSyntax(tokenList))
    }

    @Test
    fun validDeclaretionCombinatedTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun assignationHasNoCombinationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidOperatorOnAssignationValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidOperatorOnAssigantionInvalidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidOperatorOnAssignationInvalidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "c", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "8", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun HasInvalidOperatorOnAssignationValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "c", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "8", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.STRING, "holaaa", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(HasInvalidOperator().checkSyntax(tokenList))
    }
}