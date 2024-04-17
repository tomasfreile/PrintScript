package parser.ifParser

import org.junit.jupiter.api.Test
import parser.analysis.syntax.ifSyntax.ElseSyntax
import position.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.assertTrue

class IsElseSyntaxTest {
    private val isElseSyntax = ElseSyntax()

    @Test
    fun test00_IsElseSyntaxSimpleCase() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_BRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_BRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isElseSyntax.checkSyntax(tokenList)
        }
    }

    @Test
    fun test01_IsElseSyntaxWithPrint() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_BRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "printLn", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRING_LITERAL, "Hello World", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHT_BRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isElseSyntax.checkSyntax(tokenList)
        }
    }
}
