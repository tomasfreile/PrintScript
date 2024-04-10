package formatter

import ast.NilNode
import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class FormatterTest {
    val formatterPath = "src/test/resources/formatterTest.yaml"
    val formatterPath02 = "src/test/resources/formatterTest2.yaml"

    @Test
    fun test001_hello() {
        val formatter = PrintScriptFormatter(formatterPath)
        val node = NilNode
        val result = formatter.format(node)
        assertEquals("hello", result)
    }

//    @Test
//    fun test001_formatASimpleMicaelaDeclaration() {
//        val formatter = Formatter(formatterPath)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTSingleNode(
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.STRING_LITERAL, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "name", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let name: String = 'micaela';" + "\n", result)
//    }
//
//    @Test
//    fun test002_formatA2Plus2Sum() {
//        val formatter = Formatter(formatterPath)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTBinaryNode(
//                                    ASTSingleNode(
//                                        ASTSingleNode(
//                                            null,
//                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                        ),
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "sum", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let sum: int = 2 + 2;" + "\n", result)
//    }
//
//    @Test
//    fun test003_formatA2Minus2Subtraction() {
//        val formatter = Formatter(formatterPath)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTBinaryNode(
//                                    ASTSingleNode(
//                                        ASTSingleNode(
//                                            null,
//                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                        ),
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "subtraction", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let subtraction: int = 2 - 2;" + "\n", result)
//    }
//
//    @Test
//    fun test004_formatA2Star2Multiplication() {
//        val formatter = Formatter(formatterPath)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTBinaryNode(
//                                    ASTSingleNode(
//                                        ASTSingleNode(
//                                            null,
//                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                        ),
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "multiplication", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let multiplication: int = 2 * 2;" + "\n", result)
//    }
//
//    @Test
//    fun test005_formatprintln() {
//        val formatter = Formatter(formatterPath)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                null,
//                                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.STRING_LITERAL, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(
//                    TokenType.PRINT,
//                    "println",
//                    Coordinate(2, 2),
//                    Coordinate(2, 2),
//                ),
//            )
//        val result = formatter.format(node)
//        assertEquals("\n" + "println( 'micaela' );" + "\n", result)
//    }
//
//    @Test
//    fun test006_formatAComplexOperation() {
//        val formatter = Formatter(formatterPath)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTBinaryNode(
//                                    ASTSingleNode(
//                                        ASTSingleNode(
//                                            ASTSingleNode(
//                                                ASTSingleNode(
//                                                    ASTSingleNode(
//                                                        ASTSingleNode(
//                                                            ASTSingleNode(
//                                                                null,
//                                                                PrintScriptToken(
//                                                                    TokenType.SEMICOLON,
//                                                                    ";",
//                                                                    Coordinate(2, 2),
//                                                                    Coordinate(2, 2),
//                                                                ),
//                                                            ),
//                                                            PrintScriptToken(
//                                                                TokenType.RIGHT_PAREN,
//                                                                ")",
//                                                                Coordinate(2, 2),
//                                                                Coordinate(2, 2),
//                                                            ),
//                                                        ),
//                                                        PrintScriptToken(
//                                                            TokenType.STRING_LITERAL,
//                                                            "'micaela'",
//                                                            Coordinate(2, 2),
//                                                            Coordinate(2, 2),
//                                                        ),
//                                                    ),
//                                                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 2), Coordinate(2, 2)),
//                                                ),
//                                                PrintScriptToken(
//                                                    TokenType.PRINT,
//                                                    "println",
//                                                    Coordinate(2, 2),
//                                                    Coordinate(2, 2),
//                                                ),
//                                            ),
//                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                        ),
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.NUMBER_LITERAL, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER_LITERAL, "subtraction", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let subtraction: int = 2 - 2;" + "\n" + "\n" + "println( 'micaela' );" + "\n", result)
//    }
//
//    @Test
//    fun test007_formatASimpleMicaelaDeclarationWithOtherSetOfRules() {
//        val formatter = Formatter(formatterPath02)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTSingleNode(
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.STRING, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let name:String='micaela';" + "\n", result)
//    }
//
//    @Test
//    fun test008_formatA2Plus2SumWithOtherSetOfRules() {
//        val formatter = Formatter(formatterPath02)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTBinaryNode(
//                                    ASTSingleNode(
//                                        ASTSingleNode(
//                                            null,
//                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                        ),
//                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "sum", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let sum:int=2 + 2;" + "\n", result)
//    }
//
//    @Test
//    fun test009_formatprintlnWithOtherSetOfRules() {
//        val formatter = Formatter(formatterPath02)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                null,
//                                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.STRING_TYPE, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(
//                    TokenType.PRINT,
//                    "println",
//                    Coordinate(2, 2),
//                    Coordinate(2, 2),
//                ),
//            )
//        val result = formatter.format(node)
//        assertEquals("\n" + "\n" + "println( 'micaela' );" + "\n", result)
//    }
//
//    @Test
//    fun test010_formatAComplexOperationWithOtherSetOfRules() {
//        val formatter = Formatter(formatterPath02)
//        val node =
//            ASTSingleNode(
//                ASTSingleNode(
//                    ASTSingleNode(
//                        ASTSingleNode(
//                            ASTSingleNode(
//                                ASTBinaryNode(
//                                    ASTSingleNode(
//                                        ASTSingleNode(
//                                            ASTSingleNode(
//                                                ASTSingleNode(
//                                                    ASTSingleNode(
//                                                        ASTSingleNode(
//                                                            ASTSingleNode(
//                                                                null,
//                                                                PrintScriptToken(
//                                                                    TokenType.SEMICOLON,
//                                                                    ";",
//                                                                    Coordinate(2, 2),
//                                                                    Coordinate(2, 2),
//                                                                ),
//                                                            ),
//                                                            PrintScriptToken(
//                                                                TokenType.RIGHT_PAREN,
//                                                                ")",
//                                                                Coordinate(2, 2),
//                                                                Coordinate(2, 2),
//                                                            ),
//                                                        ),
//                                                        PrintScriptToken(TokenType.STRING, "'micaela'", Coordinate(2, 2), Coordinate(2, 2)),
//                                                    ),
//                                                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 2), Coordinate(2, 2)),
//                                                ),
//                                                PrintScriptToken(
//                                                    TokenType.PRINT,
//                                                    "println",
//                                                    Coordinate(2, 2),
//                                                    Coordinate(2, 2),
//                                                ),
//                                            ),
//                                            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 2), Coordinate(2, 2)),
//                                        ),
//                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    ASTSingleNode(
//                                        null,
//                                        PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 2), Coordinate(2, 2)),
//                                    ),
//                                    PrintScriptToken(TokenType.MINUS, "-", Coordinate(2, 2), Coordinate(2, 2)),
//                                ),
//                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 2), Coordinate(2, 2)),
//                            ),
//                            PrintScriptToken(TokenType.STRING_TYPE, "int", Coordinate(2, 2), Coordinate(2, 2)),
//                        ),
//                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 2), Coordinate(2, 2)),
//                    ),
//                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "subtraction", Coordinate(2, 2), Coordinate(2, 2)),
//                ),
//                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 2), Coordinate(2, 2)),
//            )
//        val result = formatter.format(node)
//        assertEquals("let subtraction:int=2 - 2;" + "\n" + "\n" + "\n" + "println( 'micaela' );" + "\n", result)
//    }
}
