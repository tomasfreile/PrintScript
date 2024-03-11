package interpreter

import org.example.interpreter.PrintScriptInterpreter
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.parser.Parser
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TypeEnum
import org.junit.Test
import kotlin.test.assertEquals

class PrintScriptInterpreterTest {
    @Test
    fun testBasicPrint() {
        val ast =
            ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(null,
                                PrintScriptToken(TypeEnum.RIGHT_PAREN, ")", Coordinate(2,3), Coordinate(2, 3))),
                        PrintScriptToken(TypeEnum.STRING, "a", Coordinate(2, 3), Coordinate(2, 3))) ,
                    PrintScriptToken(TypeEnum.LEFT_PAREN,"(", Coordinate(2, 3), Coordinate(2, 3))),
            PrintScriptToken(TypeEnum.PRINT, "println", Coordinate(2,3), Coordinate(2, 3)))

        val interpreter = PrintScriptInterpreter()
        val result = interpreter.interpret(ast)
    }

}