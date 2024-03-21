package parser.parse

import ast.Node
import parser.InvalidSyntax
import parser.Parser
import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AssignationParseTest {

    val parser: Parser = Parser()
    @Test
    fun ParseSimpleValidAssignationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val node: Node
        try{
            node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }

    @Test
    fun ParseCombinationValidAssignationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val node: Node
        try{
            node = parser.parse(tokenList)
            assertTrue(true)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(false)
        }
    }

    @Test
    fun ParseInvalidCombinationAssignationTest(){
        val tokenList: List<Token> = listOf(
            PrintScriptToken(TokenType.VALUE_IDENTIFIER, "a", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.PLUS, "+", Coordinate(2,3), Coordinate(2,3)),
            PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2,3), Coordinate(2,3))
        )
        val node: Node
        try{
            node = parser.parse(tokenList)
            assertTrue(false)
            println(node)
        }catch (e: InvalidSyntax){
            assertTrue(true)
        }
    }

}