package interpreter

import org.example.interpreter.*
import org.example.interpreter.operation.MinusOperation
import org.example.interpreter.operation.PlusOperation
import org.example.interpreter.operation.SlashOperation
import org.example.interpreter.operation.StarOperation
import org.example.parser.ASTSingleNode
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.TypeEnum
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals


class PrintScriptInterpreterTest {

    val operationInterpreter = OperationInterpreter(listOf(PlusOperation(), MinusOperation(), SlashOperation(), StarOperation()))

    val interpreterMap =  mapOf(
        Pair(TypeEnum.LEFT_PAREN, ParenInterpreter()),
        Pair(TypeEnum.RIGHT_PAREN, ParenInterpreter()),
        Pair(TypeEnum.STRING, StringInterpreter()),
        Pair(TypeEnum.PRINT, PrintInterpreter()),
        Pair(TypeEnum.NUMBER, NumberInterpreter()),
        Pair(TypeEnum.STAR, operationInterpreter),
        Pair(TypeEnum.SLASH, operationInterpreter),
        Pair(TypeEnum.PLUS, operationInterpreter),
        Pair(TypeEnum.MINUS, operationInterpreter),
    )

    private var printedCode = ""

    fun resestSystemOut(){

    }



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

        val interpreter = PrintScriptInterpreter(interpreterMap);

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        interpreter.interpret(ast)

        val printedCode = outputStream.toString().trim()
        System.setOut(System.out)

        assertEquals("a", printedCode)
    }

}