package parser

import org.junit.jupiter.api.Test
import parser.analysis.sintactic.IsAssignation
import parser.analysis.sintactic.IsDeclarative
import parser.analysis.sintactic.IsPrint
import parser.analysis.sintactic.commons.HasCombination
import parser.analysis.sintactic.commons.HasInvalidOperator
import parser.analysis.sintactic.commons.HasValidParen
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ParserSyntaxTest {
    @Test
    fun isDeclarativeValidTEst() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(IsDeclarative().checkSyntax(tokenList))
    }

    @Test
    fun isDeclarativeInvalidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(IsDeclarative().checkSyntax(tokenList))
    }

    @Test
    fun isPrintValidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(IsPrint().checkSyntax(tokenList))
    }

    @Test
    fun isPrintInvalidLengthTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(IsPrint().checkSyntax(tokenList))
    }

    @Test
    fun isPrintInvalidTokenTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(IsPrint().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombinationJustOneStringTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombinationBinaryTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Cómo estas?", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombinationWithInvalidOperatorTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Cómo estas?", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasStringCombiantionWithStringAndNumber() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasCombinationWithInvalidIdentifier() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hola, Mundo", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasNumberCombinationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasNumberCombinationWithIdentifierTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidCombinationWithStrangeOperatorTEst() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "/", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidStringCombinationWithStrangeOperatorTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SLASH, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun isAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(IsAssignation().checkSyntax(tokenList))
    }

    @Test
    fun invalidLentghForAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.PRINT, "print", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(IsAssignation().checkSyntax(tokenList))
    }

    @Test
    fun invalidAssignationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.LET, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(IsAssignation().checkSyntax(tokenList))
    }

    @Test
    fun validAssignationCombinatedTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(IsAssignation().checkSyntax(tokenList))
    }

    @Test
    fun validDeclaretionCombinatedTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_TYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun assignationHasNoCombinationTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasCombination().checkSyntax(tokenList))
    }

    @Test
    fun has2InvalidOperatorOnAssignationValidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidOperatorOnAssigantionInvalidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidOperatorOnAssignationInvalidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "8", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidOperatorOnAssignationValidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "8", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "holaaa", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun hasInvalidOperatorOnLeftParenValidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "8", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun test002HasInvalidOperatorOnLeftParenValidTest() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasInvalidOperator().checkSyntax(tokenList))
    }

    @Test
    fun test029_HasValidParenJustOnePair() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasValidParen().checkSyntax(tokenList))
    }

    @Test
    fun test030_HasInvalidParenJustOneLeftParen() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasValidParen().checkSyntax(tokenList))
    }

    @Test
    fun test031_HasInvalidParenJustOneRightParen() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasValidParen().checkSyntax(tokenList))
    }

    @Test
    fun test032_HasValidParenAtLeastTwoPairs() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue(HasValidParen().checkSyntax(tokenList))
    }

    @Test
    fun test033_HasInvalidParenOnePairAndOneLeftParen() {
        val tokenList: List<Token> =
            listOf(
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "a", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBER_LITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "c", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse(HasValidParen().checkSyntax(tokenList))
    }
}
