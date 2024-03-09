import org.example.parser.Node
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TypeEnum
import org.junit.jupiter.api.Test
import org.example.parser.Parser
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.TestFactory

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

    @Test
    fun BinaryDeclarationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "sum", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.DOUBLE_DOT, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "Number", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "4", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TypeEnum.VARIABLE_KEYWORD)
    }

    @Test
    fun TripleSumDeclarationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "sum", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.DOUBLE_DOT, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "4", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "5", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TypeEnum.VARIABLE_KEYWORD)
    }

    @Test
    fun BinaryProductTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "product", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.DOUBLE_DOT, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "*", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "4", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TypeEnum.VARIABLE_KEYWORD)
    }

    @Test
    fun BinaryDivisionTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TypeEnum.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.VALUE_IDENTIFIER, "product", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.DOUBLE_DOT, ":", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING_TYPE, "Int", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.STRING, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.PLUS, "/", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.NUMBER, "4", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val parser: Parser = Parser()
        val node: Node? = parser.parse(tokenList)
        assertNotNull(node)
        assertEquals(node?.token?.type, TypeEnum.VARIABLE_KEYWORD)
    }

    @Test
    fun BinarySubstractTest(){

    }


}