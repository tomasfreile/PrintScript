package formatter

import ast.ASTBinaryNode
import ast.ASTSingleNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.Coordinate
import token.PrintScriptToken
import token.TokenType

class FormatterTest {
    val formatterPath = "src/test/resources/formatterTest.yaml"

    @Test
    fun test001_formatASimpleMicaelaDeclaration() {
        val formatter = Formatter(formatterPath)
        val node =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTSingleNode(
                                    ASTSingleNode(
                                        null,
                                        PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    PrintScriptToken(TokenType.STRING, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 2), Coordinate(2, 2)),
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2, 2), Coordinate(2, 2)),
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
            )
        val result = formatter.format(node)
        assertEquals("let name: String = 'micaela';" + "\n", result)
    }

    @Test
    fun test002_formatA2Plus2Sum() {
        val formatter = Formatter(formatterPath)
        val node =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTBinaryNode(
                                    ASTSingleNode(
                                        ASTSingleNode(
                                            null,
                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
                                        ),
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    ASTSingleNode(
                                        null,
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 2), Coordinate(2, 2)),
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "sum", Coordinate(2, 2), Coordinate(2, 2)),
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
            )
        val result = formatter.format(node)
        assertEquals("let sum: int = 2 + 2;" + "\n", result)
    }

    @Test
    fun test003_formatA2Minus2Subtraction() {
        val formatter = Formatter(formatterPath)
        val node =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTBinaryNode(
                                    ASTSingleNode(
                                        ASTSingleNode(
                                            null,
                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
                                        ),
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    ASTSingleNode(
                                        null,
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 2), Coordinate(2, 2)),
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "subtraction", Coordinate(2, 2), Coordinate(2, 2)),
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
            )
        val result = formatter.format(node)
        assertEquals("let subtraction: int = 2 - 2;" + "\n", result)
    }

    @Test
    fun test004_formatA2Star2Multiplication() {
        val formatter = Formatter(formatterPath)
        val node =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTBinaryNode(
                                    ASTSingleNode(
                                        ASTSingleNode(
                                            null,
                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
                                        ),
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    ASTSingleNode(
                                        null,
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 2), Coordinate(2, 2)),
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "multiplication", Coordinate(2, 2), Coordinate(2, 2)),
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
            )
        val result = formatter.format(node)
        assertEquals("let multiplication: int = 2 * 2;" + "\n", result)
    }

    @Test
    fun test005_formatprintln() {
        val formatter = Formatter(formatterPath)
        val node =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                null,
                                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
                            ),
                            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 2), Coordinate(2, 2)),
                        ),
                        PrintScriptToken(TokenType.STRING, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
                    ),
                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 2), Coordinate(2, 2)),
                ),
                PrintScriptToken(
                    TokenType.PRINT,
                    "println",
                    Coordinate(2, 2),
                    Coordinate(2, 2),
                ),
            )
        val result = formatter.format(node)
        assertEquals("\n" + "println( 'micaela' );" + "\n", result)
    }

    @Test
    fun test006_formatAComplexOperation() {
        val formatter = Formatter(formatterPath)
        val node =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTBinaryNode(
                                    ASTSingleNode(
                                        ASTSingleNode(
                                            ASTSingleNode(
                                                ASTSingleNode(
                                                    ASTSingleNode(
                                                        ASTSingleNode(
                                                            ASTSingleNode(
                                                                null,
                                                                PrintScriptToken(
                                                                    TokenType.SEMICOLON,
                                                                    ";",
                                                                    Coordinate(2, 2),
                                                                    Coordinate(2, 2),
                                                                ),
                                                            ),
                                                            PrintScriptToken(
                                                                TokenType.RIGHT_PAREN,
                                                                ")",
                                                                Coordinate(2, 2),
                                                                Coordinate(2, 2),
                                                            ),
                                                        ),
                                                        PrintScriptToken(TokenType.STRING, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
                                                    ),
                                                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 2), Coordinate(2, 2)),
                                                ),
                                                PrintScriptToken(
                                                    TokenType.PRINT,
                                                    "println",
                                                    Coordinate(2, 2),
                                                    Coordinate(2, 2),
                                                ),
                                            ),
                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
                                        ),
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    ASTSingleNode(
                                        null,
                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
                                    ),
                                    PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 2), Coordinate(2, 2)),
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "subtraction", Coordinate(2, 2), Coordinate(2, 2)),
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
            )
        val result = formatter.format(node)
        assertEquals("let subtraction: int = 2 - 2;" + "\n" + "\n" + "println( 'micaela' );" + "\n", result)
    }
}
