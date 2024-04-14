package formatter

import ast.AssignmentNode
import ast.AstNode
import ast.BinaryOperationNode
import ast.LiteralNode
import ast.PrintNode
import ast.VariableDeclarationNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import position.Coordinate
import position.TokenPosition
import token.TokenType

class FormatterTest {
    private val formatterPath01 = "src/test/resources/formatterTest01.yaml"
    private val formatterPath02 = "src/test/resources/formatterTest02.yaml"

    @Test
    fun test001_formatASimpleMicaelaDeclaration() {
        val node: AstNode =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRING_TYPE,
                LiteralNode("micaela", TokenType.STRING_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let name: string = \"micaela\";" + "\n", result)
    }

    @Test
    fun test002_formatASimpleMicaelaDeclarationWithOtherSetOfRules() {
        val node: AstNode =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRING_TYPE,
                LiteralNode("micaela", TokenType.STRING_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let name:string=\"micaela\";" + "\n", result)
    }

    @Test
    fun test003_formatASimpleNumberDeclaration() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBER_TYPE,
                LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let num: number = 2;" + "\n", result)
    }

    @Test
    fun test004_formatASimpleNumberDeclarationWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBER_TYPE,
                LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let num:number=2;" + "\n", result)
    }

    @Test
    fun test005_formatASimpleSum() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "sum",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let sum: number = 2 + 2;" + "\n", result)
    }

    @Test
    fun test006_formatASimpleSumWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "sum",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let sum:number=2 + 2;" + "\n", result)
    }

    @Test
    fun test007_formatASimpleSubtraction() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "subtraction",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.MINUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let subtraction: number = 2 - 2;" + "\n", result)
    }

    @Test
    fun test08_formatASimpleSubtractionWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "subtraction",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.MINUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let subtraction:number=2 - 2;" + "\n", result)
    }

    @Test
    fun test009_formatASimpleMultiplication() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "multiplication",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.STAR,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let multiplication: number = 2 * 2;" + "\n", result)
    }

    @Test
    fun test010_formatASimpleMultiplicationWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "multiplication",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.STAR,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let multiplication:number=2 * 2;" + "\n", result)
    }

    @Test
    fun test011_formatASimpleDivision() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "division",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.SLASH,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let division: number = 2 / 2;" + "\n", result)
    }

    @Test
    fun test012_formatASimpleDivisionWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "division",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBER_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.SLASH,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let division:number=2 / 2;" + "\n", result)
    }

    @Test
    fun test013_formatASimplePrintDeclaration() {
        val node =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBER_LITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("\n" + "println(1);" + "\n", result)
    }

    @Test
    fun test014_formatASimplePrintDeclarationWithOtherSetOfRules() {
        val node =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBER_LITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("\n" + "\n" + "println(1);" + "\n", result)
    }

    @Test
    fun test015_formatASimpleAssignment() {
        val node =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRING_LITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("name = \"micaela\";" + "\n", result)
    }

    @Test
    fun test016_formatASimpleAssignmentWithOtherSetOfRules() {
        val node =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRING_LITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("name=\"micaela\";" + "\n", result)
    }

    @Test
    fun test017_formatAComplexLeftBinaryOperation() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    LiteralNode(
                        "3",
                        TokenType.NUMBER_LITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.STAR,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let result: number = (2 + 2) * 3;" + "\n", result)
    }

    @Test
    fun test018_formatAComplexLeftBinaryOperationWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    LiteralNode(
                        "3",
                        TokenType.NUMBER_LITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.STAR,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let result:number=(2 + 2) * 3;" + "\n", result)
    }

    @Test
    fun test019_formatAComplexRightBinaryOperation() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBER_LITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.STAR,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let result: number = 2 + (2 * 3);" + "\n", result)
    }

    @Test
    fun test020_formatAComplexRightBinaryOperationWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBER_LITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.STAR,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let result:number=2 + (2 * 3);" + "\n", result)
    }

    @Test
    fun test021_formatAComplexBinaryOperation() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "3",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "1",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.MINUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.STAR,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let result: number = (2 + 2) * (3 - 1);" + "\n", result)
    }

    @Test
    fun test022_formatAComplexBinaryOperationWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBER_TYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "3",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "1",
                            TokenType.NUMBER_LITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.MINUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    TokenType.STAR,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let result:number=(2 + 2) * (3 - 1);" + "\n", result)
    }

    @Test
    fun test023_formatAConstDeclaration() {
        val node =
            VariableDeclarationNode(
                TokenType.CONST,
                "name",
                TokenType.STRING_TYPE,
                LiteralNode("micaela", TokenType.STRING_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("const name: string = \"micaela\";" + "\n", result)
    }

    @Test
    fun test024_formatAConstDeclarationWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.CONST,
                "name",
                TokenType.STRING_TYPE,
                LiteralNode("micaela", TokenType.STRING_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("const name:string=\"micaela\";" + "\n", result)
    }

    @Test
    fun test025_formatASimpleBooleanDeclaration() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "isTrue",
                TokenType.BOOLEAN_TYPE,
                LiteralNode(
                    "True",
                    TokenType.BOOLEAN_LITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("let isTrue: boolean = True;" + "\n", result)
    }

    @Test
    fun test026_formatASimpleBooleanDeclarationWithOtherSetOfRules() {
        val node =
            VariableDeclarationNode(
                TokenType.LET,
                "isTrue",
                TokenType.BOOLEAN_TYPE,
                LiteralNode(
                    "True",
                    TokenType.BOOLEAN_LITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let isTrue:boolean=True;" + "\n", result)
    }

//    @Test
//    fun test027_formatAnIfOperation() {
//        val node =
//            IfNode(
//                LiteralNode("True", TokenType.BOOLEAN_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
//                AssignmentNode(
//                    "name",
//                    LiteralNode("micaela", TokenType.STRING_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
//                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
//                ),
//                PrintNode(
//                    LiteralNode("False", TokenType.BOOLEAN_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
//                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
//                ),
//                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
//            )
//        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
//        val result = formatter.format(node)
//        assertEquals("if (True) {\n\tname = \"micaela\";\n\n} else {\n\n\tprintln(False);\n\n}", result)
//    }
//
//    @Test
//    fun test028_formatAnIfOperationWithOtherSetOfRules() {
//        val node =
//            IfNode(
//                LiteralNode("True", TokenType.BOOLEAN_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
//                AssignmentNode(
//                    "name",
//                    LiteralNode("micaela", TokenType.STRING_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
//                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
//                ),
//                PrintNode(
//                    LiteralNode("False", TokenType.BOOLEAN_LITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
//                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
//                ),
//                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
//            )
//        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
//        val result = formatter.format(node)
//        assertEquals("if (true) {\nname=\"micaela\";\n} else {\nprintln(False);\n}", result)
//    }

//
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
}
