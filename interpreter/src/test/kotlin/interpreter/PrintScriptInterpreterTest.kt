package interpreter

import ast.AstNode
import interpreter.builder.InterpreterBuilder
import interpreter.variable.Variable
import lexer.PrintScriptLexer
import lexer.getTokenMapV11
import org.junit.jupiter.api.Test
import parser.PrintScriptParser
import parser.analysis.semantic.BooleanSemantic
import parser.analysis.semantic.NumberSemantic
import parser.analysis.semantic.SemanticRule
import parser.analysis.semantic.StringSemantic
import parser.analysis.syntax.expression.Expression
import parser.analysis.syntax.expression.IsArithmeticExpression
import parser.analysis.syntax.expression.IsBooleanExpression
import parser.analysis.syntax.expression.IsStringExpression
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.BooleanNodeBuilder
import parser.nodeBuilder.NodeBuilder
import parser.nodeBuilder.StringNodeBuilder
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
            DeclarationParser(TokenType.SEMICOLON, getTypeMap(), getDeclarationList(), getSyntaxMap(), getSemanticMap(), getNodeBuilders()),
            PrintParser(TokenType.SEMICOLON, getSyntaxMap(), getSemanticMap(), getNodeBuilders()),
            AssignationParser(TokenType.SEMICOLON, getSyntaxMap(), getSemanticMap(), getNodeBuilders()),
        )
    }

    private fun getSemanticMap(): Map<TokenType, SemanticRule> {
        return mapOf(
            Pair(TokenType.NUMBER_TYPE, NumberSemantic()),
            Pair(TokenType.STRING_TYPE, StringSemantic()),
            Pair(TokenType.BOOLEAN_TYPE, BooleanSemantic()),
        )
    }

    private fun getDeclarationList(): List<TokenType> {
        return listOf(
            TokenType.LET,
            TokenType.CONST,
        )
    }

    private fun getTypeMap(): Map<TokenType, TokenType> {
        return mapOf(
            Pair(TokenType.NUMBER_LITERAL, TokenType.NUMBER_TYPE),
            Pair(TokenType.STRING_LITERAL, TokenType.STRING_TYPE),
            Pair(TokenType.BOOLEAN_LITERAL, TokenType.BOOLEAN_TYPE),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, Expression> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, IsStringExpression()),
            Pair(TokenType.NUMBER_TYPE, IsArithmeticExpression()),
            Pair(TokenType.BOOLEAN_TYPE, IsBooleanExpression()),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBER_TYPE, ArithmeticNodeBuilder()),
            Pair(TokenType.BOOLEAN_TYPE, BooleanNodeBuilder()),
        )
    }

    private fun getTree(code: String): AstNode? {
        val tokenList = lexer.lex(code)
//        for (token in tokenList) {
//            println(token.value + " "+ token.type)
//        }
        return parser.parse(tokenList)
    }

    @Test
    fun testBasicPrint() {
        val string = "println(1);"
        val result = interpreter.interpret(getTree(string), symbolTable)
        assertEquals(1, result)
    }

    @Test
    fun variableDeclarationTest() {
        val string = "let num: number = 3;"
        val result = interpreter.interpret(getTree(string), symbolTable)
        assertEquals(3, result)
    }

    @Test
    fun testStringPlusNumberVariableDeclaration() {
        val string = "println('tista' + 3);"
        val result = interpreter.interpret(getTree(string), symbolTable)
        assertEquals("tista3", result)
    }

    @Test
    fun testDeclareVariableAndThenPrintIt() {
        val string = "let num: number = 3;"
        interpreter.interpret(getTree(string), symbolTable)
        val string2 = "println(num);"
        val result = interpreter.interpret(getTree(string2), symbolTable)
        assertEquals(3, result)
    }
}
