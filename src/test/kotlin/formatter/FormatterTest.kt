package formatter

import org.example.formatter.Formatter
import org.example.parser.ASTSingleNode
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.TypeEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FormatterTest {

    @Test
    fun getYamlDataTest(){
        val formatter = Formatter()
        val map = formatter.readYaml()
        val expectedFormatting = mapOf(
            "colon" to mapOf("before" to false, "after" to false),
            "assignation" to mapOf("before" to false, "after" to false),
            "print" to mapOf("jump" to 0)
        )
        assertEquals(expectedFormatting, map["formatting"])
    }

    @Test
    fun formatASimpleMicaelaDeclaration(){
        val formatter = Formatter()
        val node = ASTSingleNode(
            ASTSingleNode(
                ASTSingleNode(
                    ASTSingleNode(
                        ASTSingleNode(
                            ASTSingleNode(
                                ASTSingleNode(
                                    null,
                                    PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,2), Coordinate(2,2))
                                ),
                                PrintScriptToken(TypeEnum.STRING, "micaela", Coordinate(2,2), Coordinate(2,2))
                            ),
                            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,2), Coordinate(2,2))
                        ),
                        PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(2,2), Coordinate(2,2))
                    ),
                    PrintScriptToken(TypeEnum.COLON, ":",Coordinate(2,2), Coordinate(2,2))
                ),
                PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,2), Coordinate(2,2))
            ),
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,2), Coordinate(2,2))
        )
        val result = formatter.format(node)
        assertEquals("let name: String = micaela", result)
    }

}