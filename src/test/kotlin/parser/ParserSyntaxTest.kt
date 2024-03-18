package parser

import org.example.parser.sintactic.declarative.hasNoneConsecutiveValue
import org.example.parser.sintactic.declarative.hasOperatorOnProperWay
import org.example.parser.sintactic.declarative.isDeclarative
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TypeEnum
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParserSyntaxTest {

    @Test
    fun isDeclarativeValidTEst(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(isDeclarative().checkSyntax(tokenList))
    }

    @Test
    fun isDeclarativeInvalidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(isDeclarative().checkSyntax(tokenList))
    }

    @Test
    fun hasPlusOnProperWayValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasOperatorOnProperWay().checkSyntax(tokenList))
    }

    @Test
    fun hasPlusOnProperWayInvlaidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasOperatorOnProperWay().checkSyntax(tokenList))
    }

    @Test
    fun hasPlusPlusOnProperWayInvlaidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasOperatorOnProperWay().checkSyntax(tokenList))
    }

    @Test
    fun hasOperatorOnProperWayStarValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STAR, "*", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasOperatorOnProperWay().checkSyntax(tokenList))
    }

    @Test
    fun hasOperatorOnProperWayMinusInvalidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STAR, "*", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STAR, "-", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasOperatorOnProperWay().checkSyntax(tokenList))
    }

    @Test
    fun hasOperatorOnProperWayStrangeCaseInvalidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STAR, "*", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STAR, "*", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasOperatorOnProperWay().checkSyntax(tokenList))
    }

    @Test
    fun hasNoneConsecutiveValidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "String", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "Micalea", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "Rosada", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertTrue(hasNoneConsecutiveValue().checkSyntax(tokenList))
    }

    @Test
    fun hasNoneConsecutiveValueInvalidTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.COLON, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER_TYPE, "String", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "Micalea", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "Rosada", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        assertFalse(hasNoneConsecutiveValue().checkSyntax(tokenList))
    }
}