package interpreter

import ast.ASTBinaryNode
import ast.ASTSingleNode
import interpreter.builder.InterpreterBuilder
import org.junit.jupiter.api.Test
import token.Coordinate
import token.PrintScriptToken
import token.TokenType
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class PrintScriptInterpreterTest {
    private val interpreter = InterpreterBuilder().build()

    @Test
    fun testBasicPrint() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            null,
                            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                        ),
                        PrintScriptToken(TokenType.STRING, "a", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
            )

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        interpreter.interpret(ast)

        val printedCode = outputStream.toString().trim()
        System.setOut(System.out)

        assertEquals("a", printedCode)
    }

    @Test
    fun variableDeclarationTest() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTSingleNode(
                                    null,
                                    PrintScriptToken(TokenType.STRING, "tista", Coordinate(2, 3), Coordinate(2, 3)),
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "STRING", Coordinate(2, 3), Coordinate(2, 3)),
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
            )

        val nextInterpreter = interpreter.interpret(ast)
        val nextSymbolTable = nextInterpreter.symbolTable
        val key = nextSymbolTable.keys.elementAt(0)
        val token = nextSymbolTable.values.elementAt(0)

        assertEquals("name", key)
        assertEquals(TokenType.STRING_TYPE, token.type)
        assertEquals("tista", token.value)
    }

    @Test
    fun testStringPlusNumberVariableDeclaration() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTBinaryNode(
                                    ASTSingleNode(null, PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3))),
                                    ASTSingleNode(
                                        null,
                                        PrintScriptToken(TokenType.STRING, "tista", Coordinate(2, 3), Coordinate(2, 3)),
                                    ),
                                    PrintScriptToken(
                                        TokenType.PLUS,
                                        "+",
                                        Coordinate(2, 3),
                                        Coordinate(2, 3),
                                    ),
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "STRING", Coordinate(2, 3), Coordinate(2, 3)),
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2, 3), Coordinate(2, 3)),
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3)),
            )

        val nextInterpreter = interpreter.interpret(ast)
        val nextSymbolTable = nextInterpreter.symbolTable
        val key = nextSymbolTable.keys.elementAt(0)
        val token = nextSymbolTable.values.elementAt(0)

        assertEquals("name", key)
        assertEquals(TokenType.STRING_TYPE, token.type)
        assertEquals("tista3", token.value)
    }
}
