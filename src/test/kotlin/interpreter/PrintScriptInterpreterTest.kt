package interpreter

import org.example.interpreter.ParenInterpreter
import org.example.interpreter.PrintInterpreter
import org.example.interpreter.PrintScriptInterpreter
import org.example.interpreter.StringInterpreter
import org.example.parser.ASTSingleNode
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.TypeEnum
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals


class PrintScriptInterpreterTest {
    @Test
    fun testBasicPrint() {
        val ast =
            ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(null,
                                PrintScriptToken(TypeEnum.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2, 3))
                            ),
                        PrintScriptToken(TypeEnum.STRING, "a", Coordinate(2, 3), Coordinate(2, 3))) ,
                    PrintScriptToken(TypeEnum.LEFT_PAREN,"(", Coordinate(2, 3), Coordinate(2, 3))),
            PrintScriptToken(TypeEnum.PRINT, "println", Coordinate(2,3), Coordinate(2, 3)))

        val interpreter = PrintScriptInterpreter(mapOf(
            Pair(TypeEnum.LEFT_PAREN, ParenInterpreter()),
            Pair(TypeEnum.RIGHT_PAREN, ParenInterpreter()),
            Pair(TypeEnum.STRING, StringInterpreter()),
            Pair(TypeEnum.PRINT, PrintInterpreter())
        ));
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        interpreter.interpret(ast)
        // Retrieve the printed content
        val printedCode = outputStream.toString().trim()
        System.setOut(System.out)

        assertEquals("a", printedCode)

    }

}