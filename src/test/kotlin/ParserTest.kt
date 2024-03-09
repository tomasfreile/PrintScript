import org.example.parser.Node
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TypeEnum
import org.junit.jupiter.api.Test
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.*

class ParserTest {

    @Test
    fun StringDeclarationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "name", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.DOUBLE_DOT, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "String", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "marcos", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TypeEnum.VARIABLE_KEYWORD)

    }

}