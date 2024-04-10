package interpreter

import ast.AstNode
import interpreter.builder.InterpreterBuilder
import interpreter.result.PrintResult
import interpreter.result.Result
import interpreter.variable.Variable
import lexer.PrintScriptLexer
import lexer.getTokenMapV11
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import parser.PrintScriptParser
import parser.parser.AssignationParser
import parser.parser.DeclarationParser
import parser.parser.Parser
import parser.parser.PrintParser
import token.TokenType
import kotlin.test.assertEquals

class PrintScriptInterpreterTest {
    private val interpreter = InterpreterBuilder().build()
    private val lexer = PrintScriptLexer(getTokenMapV11())
    private val parser = PrintScriptParser(getParsers())
    private val symbolTable = mutableMapOf<Variable, Any>()

    private fun getParsers(): List<Parser> {
        return listOf(
            DeclarationParser(TokenType.SEMICOLON, getMap()),
            PrintParser(TokenType.SEMICOLON),
            AssignationParser(TokenType.SEMICOLON),
        )
    }

    private fun getMap(): Map<TokenType, TokenType> {
        return mapOf(
            Pair(TokenType.NUMBER_LITERAL, TokenType.NUMBER_TYPE),
            Pair(TokenType.STRING_LITERAL, TokenType.STRING_TYPE),
            Pair(TokenType.BOOLEAN_LITERAL, TokenType.BOOLEAN_TYPE),
        )
    }

    private fun getTree(code: String): AstNode {
        val tokenList = lexer.lex(code)
//        for (token in tokenList) {
//            println(token.value + " "+ token.type)
//        }
        return parser.parse(tokenList)
    }

    @BeforeEach
    fun clearSymbolTable() {
        symbolTable.clear()
    }

    @Test
    fun testBasicPrint() {
        val string = "println(1);"
        val result = interpreter.interpret(getTree(string), symbolTable)
        result as PrintResult
        assertEquals("1", result.toPrint)
    }

    @Test
    fun variableDeclarationTest() {
        val string = "let num: number = 3;"
        val result = interpreter.interpret(getTree(string), symbolTable)
        result as Result
        assertEquals(3, result.value)
    }

    @Test
    fun testStringPlusNumberVariableDeclaration() {
        val string = "println('tista' + 3);"
        val result = interpreter.interpret(getTree(string), symbolTable)
        result as PrintResult
        assertEquals("tista3", result.toPrint)
    }

    @Test
    fun testDeclareVariableAndThenPrintIt() {
        val string = "let num: number = 3;"
        interpreter.interpret(getTree(string), symbolTable)
        val string2 = "println(num);"
        val result = interpreter.interpret(getTree(string2), symbolTable)
        result as PrintResult
        assertEquals("3", result.toPrint)
    }
}
