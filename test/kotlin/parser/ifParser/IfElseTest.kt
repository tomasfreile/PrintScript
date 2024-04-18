package parser.ifParser

import parser.analysis.syntax.rule.ifSyntax.IsIfElseSyntax
import position.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.Test
import kotlin.test.assertTrue

class IfElseTest {
    private val isIfElse = IsIfElseSyntax()

    @Test
    fun test00_IsIfElseSimpleCase() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isIfElse.checkSyntax(tokenList)
        }
    }

    @Test
    fun test01_IsIfElseNestedInThenBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isIfElse.checkSyntax(tokenList)
        }
    }

    @Test
    fun test02_IsIfElseNestedInElseBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(
                    TokenType.RIGHTBRACE,
                    "}",
                    Coordinate(2, 3),
                    Coordinate(2, 3),
                ),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isIfElse.checkSyntax(tokenList)
        }
    }

    @Test
    fun test03_IsIfElseNestedIfInBothBlocks() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isIfElse.checkSyntax(tokenList)
        }
    }

    @Test
    fun test04_IsIfElseNestedIfTwoTimesInThenBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isIfElse.checkSyntax(tokenList)
        }
    }

    @Test
    fun test05_IsIfElseDoubleNestedInThenBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isIfElse.checkSyntax(tokenList)
        }
    }

    @Test
    fun test06_IsIfElseDoubleNestedInThenBlockWithSimpleIf() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            isIfElse.checkSyntax(tokenList)
        }
    }
}
