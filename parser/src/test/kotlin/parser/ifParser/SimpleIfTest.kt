package parser.ifParser

import parser.analysis.syntax.ifSyntax.IfSyntax
import position.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class SimpleIfTest {
    private val simpleIf = IfSyntax()

    @Test
    fun test00_IsSimpleIfSyntax() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERTYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            simpleIf.checkSyntax(tokenList)
        }
    }

    @Test
    fun test01_IsSimpleIfSyntaxNestedIf() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            simpleIf.checkSyntax(tokenList)
        }
    }

    @Test
    fun test02_IsSimpleIfSyntaxTwoTimesNestedIf() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            simpleIf.checkSyntax(tokenList)
        }
    }

    @Test
    fun test03_IsSimpleSyntaxThreeTimesNestedIfAndOneNestedIsNested() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(
                    TokenType.SEMICOLON,
                    ";",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            simpleIf.checkSyntax(tokenList)
        }
    }

    @Test
    fun test04_IfSimpleSyntaxInvalidEmptyBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertFalse {
            simpleIf.checkSyntax(tokenList)
        }
    }
}
