package interpreter

import org.example.interpreter.*
import org.example.interpreter.operation.MinusOperation
import org.example.interpreter.operation.PlusOperation
import org.example.interpreter.operation.SlashOperation
import org.example.interpreter.operation.StarOperation
import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TokenType
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals


class PrintScriptInterpreterTest {

    val operationInterpreter =
        OperationInterpreter(listOf(PlusOperation(), MinusOperation(), SlashOperation(), StarOperation()))

    val interpreterMap = mapOf(
        Pair(TokenType.LEFT_PAREN, ParenInterpreter()),
        Pair(TokenType.RIGHT_PAREN, ParenInterpreter()),
        Pair(TokenType.STRING, StringInterpreter()),
        Pair(TokenType.PRINT, PrintInterpreter()),
        Pair(TokenType.NUMBER, NumberInterpreter()),
        Pair(TokenType.STAR, operationInterpreter),
        Pair(TokenType.SLASH, operationInterpreter),
        Pair(TokenType.PLUS, operationInterpreter),
        Pair(TokenType.MINUS, operationInterpreter),
        Pair(TokenType.VARIABLE_KEYWORD, DeclarationInterpreter()),
        Pair(TokenType.VALUE_IDENTIFIER, ValueIdentifierInterpreter()),
        Pair(TokenType.ASSIGNATION, AssignationInterpreter())
    )

    val symbolTable = emptyMap<String, Token>()


    @Test
    fun testBasicPrint() {
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            null,
                            PrintScriptToken(TokenType.RIGHT_PAREN, ")", Coordinate(2, 3), Coordinate(2, 3))
                        ),
                        PrintScriptToken(TokenType.STRING, "a", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    PrintScriptToken(TokenType.LEFT_PAREN, "(", Coordinate(2, 3), Coordinate(2, 3))
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3))
            )

        val interpreter = PrintScriptInterpreter(interpreterMap, symbolTable);

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
                                ASTSingleNode(null,

                                    PrintScriptToken(TokenType.STRING, "tista", Coordinate(2, 3), Coordinate(2, 3))
                                ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3))
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "STRING", Coordinate(2, 3), Coordinate(2, 3))
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2, 3), Coordinate(2, 3))
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3))
            )

        val interpreter = PrintScriptInterpreter(interpreterMap, symbolTable);
        val nextInterpreter = interpreter.interpret(ast)
        val nextSymbolTable = nextInterpreter.symbolTable
        val key = nextSymbolTable.keys.elementAt(0)
        val token = nextSymbolTable.values.elementAt(0)

        assertEquals("name", key)
        assertEquals(TokenType.STRING_TYPE, token.type)
        assertEquals("tista", token.value)

    }


    @Test
    fun testStringPlusNumberVariableDeclaration(){
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                    ASTBinaryNode(
                                        ASTSingleNode(null,  PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3))),
                                        ASTSingleNode(null,  PrintScriptToken(TokenType.STRING, "tista", Coordinate(2, 3), Coordinate(2, 3))),
                                        PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)
                                        )
                                    ),
                                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3))
                            ),
                            PrintScriptToken(TokenType.STRING_TYPE, "STRING", Coordinate(2, 3), Coordinate(2, 3))
                        ),
                        PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2, 3), Coordinate(2, 3))
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2, 3), Coordinate(2, 3))
            )


        val interpreter = PrintScriptInterpreter(interpreterMap, symbolTable);
        val nextInterpreter = interpreter.interpret(ast)
        val nextSymbolTable = nextInterpreter.symbolTable
        val key = nextSymbolTable.keys.elementAt(0)
        val token = nextSymbolTable.values.elementAt(0)

        assertEquals("name", key)
        assertEquals(TokenType.STRING_TYPE, token.type)
        assertEquals("tista3", token.value)
    }





}