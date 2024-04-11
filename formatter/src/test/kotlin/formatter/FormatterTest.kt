package formatter

import ast.AstNode
import lexer.PrintScriptLexer
import lexer.getTokenMapV11
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import parser.parserBuilder.PrintScriptOnePointOneParserBuilder

class FormatterTest {
    private val formatterPath01 = "src/test/resources/formatterTest01.yaml"
    private val formatterPath02 = "src/test/resources/formatterTest02.yaml"
    private val lexer = PrintScriptLexer(getTokenMapV11())
    private val parser = PrintScriptOnePointOneParserBuilder().build()

    private fun getTree(code: String): AstNode? {
        val tokenList = lexer.lex(code)
        return parser.createAST(tokenList)
    }

    @Test
    fun test001_formatASimpleMicaelaDeclaration() {
        val string = "let name:string = 'micaela';"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let name: string = \"micaela\";" + "\n", result)
    }

    @Test
    fun test002_formatASimpleMicaelaDeclarationWithOtherSetOfRules() {
        val string = "let name:string = 'micaela';"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let name:string=\"micaela\";" + "\n", result)
    }

    @Test
    fun test003_formatASimpleBooleanDeclaration() {
        val string = "let isTrue:boolean = True;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let isTrue: boolean = True;" + "\n", result)
    }

    @Test
    fun test004_formatASimpleBooleanDeclarationWithOtherSetOfRules() {
        val string = "let isTrue:boolean = True;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let isTrue:boolean=True;" + "\n", result)
    }

    @Test
    fun test005_formatASimpleNumberDeclaration() {
        val string = "let num:number = 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let num: number = 2;" + "\n", result)
    }

    @Test
    fun test006_formatASimpleNumberDeclarationWithOtherSetOfRules() {
        val string = "let num:number = 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let num:number=2;" + "\n", result)
    }

    @Test
    fun test007_formatASimpleSum() {
        val string = "let sum:number = 2 + 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let sum: number = 2 + 2;" + "\n", result)
    }

    @Test
    fun test008_formatASimpleSumWithOtherSetOfRules() {
        val string = "let sum:number = 2 + 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let sum:number=2 + 2;" + "\n", result)
    }

    @Test
    fun test009_formatASimpleSubtraction() {
        val string = "let subtraction:number = 2 - 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let subtraction: number = 2 - 2;" + "\n", result)
    }

    @Test
    fun test010_formatASimpleSubtractionWithOtherSetOfRules() {
        val string = "let subtraction:number = 2 - 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let subtraction:number=2 - 2;" + "\n", result)
    }

    @Test
    fun test011_formatASimpleMultiplication() {
        val string = "let multiplication:number = 2 * 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let multiplication: number = 2 * 2;" + "\n", result)
    }

    @Test
    fun test012_formatASimpleMultiplicationWithOtherSetOfRules() {
        val string = "let multiplication:number = 2 * 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let multiplication:number=2 * 2;" + "\n", result)
    }

    @Test
    fun test013_formatASimpleDivision() {
        val string = "let division:number = 2 / 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let division: number = 2 / 2;" + "\n", result)
    }

    @Test
    fun test014_formatASimpleDivisionWithOtherSetOfRules() {
        val string = "let division:number = 2 / 2;"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("let division:number=2 / 2;" + "\n", result)
    }

    @Test
    fun test015_formatAConstDeclaration() {
        val string = "const name:string = 'micaela';"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("const name: string = \"micaela\";" + "\n", result)
    }

    @Test
    fun test016_formatAConstDeclarationWithOtherSetOfRules() {
        val string = "const name:string = 'micaela';"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("const name:string=\"micaela\";" + "\n", result)
    }

    @Test
    fun test017_formatASimplePrintDeclaration() {
        val string = "println(1);"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("\n" + "println(1);" + "\n", result)
    }

    @Test
    fun test018_formatASimplePrintDeclarationWithOtherSetOfRules() {
        val string = "println(1);"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("\n" + "\n" + "println(1);" + "\n", result)
    }

    @Test
    fun test019_formatASimpleAssignment() {
        val string = "name = 'micaela';"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = ast?.let { formatter.format(it) }
        assertEquals("name = \"micaela\";" + "\n", result)
    }

    @Test
    fun test020_formatASimpleAssignmentWithOtherSetOfRules() {
        val string = "name = 'micaela';"
        val ast = getTree(string)
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = ast?.let { formatter.format(it) }
        assertEquals("name=\"micaela\";" + "\n", result)
    }

//    @Test
//    fun test021_formatASimpleReadInputFunction() {
//        val string = "condition = readInput(hello);"
//        val ast = getTree(string)
//        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
//        val result = ast?.let { formatter.format(it) }
//        assertEquals("condition = readInput(hello);" + "\n", result)
//    }
//
//    @Test
//    fun test022_formatASimpleReadInputFunctionWithOtherSetOfRules() {
//        val string = "condition = readInput('hello');"
//        val ast = getTree(string)
//        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
//        val result = ast?.let { formatter.format(it) }
//        assertEquals("condition=readInput(\"hello\");" + "\n", result)
//    }
//
//    @Test
//    fun test023_formatAnIfOperation() {
//        val node =
//            IfNode(
//                LiteralNode("true", TokenType.BOOLEAN_LITERAL),
//                AssignmentNode("name", LiteralNode("micaela", TokenType.STRING_LITERAL)),
//                PrintNode(LiteralNode("False", TokenType.BOOLEAN_LITERAL)),
//            )
//        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
//        val result = formatter.format(node)
//        assertEquals("if (true) {\n    name = \"micaela\";\n} else {\n    println(False);\n}", result)
//    }
//
//    @Test
//    fun test024_formatAnIfOperationWithOtherSetOfRules() {
//        val node =
//            IfNode(
//                LiteralNode("true", TokenType.BOOLEAN_LITERAL),
//                AssignmentNode("name", LiteralNode("micaela", TokenType.STRING_LITERAL)),
//                PrintNode(LiteralNode("False", TokenType.BOOLEAN_LITERAL)),
//            )
//        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
//        val result = formatter.format(node)
//        assertEquals("if (true) {\nname=\"micaela\";\n} else {\nprintln(False);\n}", result)
//    }
}
