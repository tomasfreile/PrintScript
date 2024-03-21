package formatter


import ast.ASTSingleNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.Coordinate
import token.PrintScriptToken
import token.TokenType

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
                                    PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,2), Coordinate(2,2))
                                ),
                                PrintScriptToken(TokenType.STRING, "micaela", Coordinate(2,2), Coordinate(2,2))
                            ),
                            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,2), Coordinate(2,2))
                        ),
                        PrintScriptToken(TokenType.STRING_TYPE, "String", Coordinate(2,2), Coordinate(2,2))
                    ),
                    PrintScriptToken(TokenType.COLON, ":",Coordinate(2,2), Coordinate(2,2))
                ),
                PrintScriptToken(TokenType.VALUE_IDENTIFIER, "name", Coordinate(2,2), Coordinate(2,2))
            ),
            PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,2), Coordinate(2,2))
        )
        val result = formatter.format(node)
        assertEquals("let name: String = micaela", result)
    }

}