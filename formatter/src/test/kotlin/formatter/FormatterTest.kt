package formatter

import ast.AssignmentNode
import ast.AstNode
import ast.BinaryOperationNode
import ast.CodeBlock
import ast.FunctionNode
import ast.IfNode
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
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                    TokenType.NUMBERLITERAL,
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
                    TokenType.NUMBERLITERAL,
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
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
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
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    LiteralNode(
                        "3",
                        TokenType.NUMBERLITERAL,
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    LiteralNode(
                        "3",
                        TokenType.NUMBERLITERAL,
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "1",
                            TokenType.NUMBERLITERAL,
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
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        TokenType.PLUS,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "1",
                            TokenType.NUMBERLITERAL,
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
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
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
                TokenType.BOOLEANTYPE,
                LiteralNode(
                    "True",
                    TokenType.BOOLEANLITERAL,
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
                TokenType.BOOLEANTYPE,
                LiteralNode(
                    "True",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals("let isTrue:boolean=True;" + "\n", result)
    }

    @Test
    fun test027_formatASimpleCodeBlock() {
        val node1 =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val nodeList = listOf(node1, node2)
        val codeBlock: AstNode = CodeBlock(nodeList, TokenPosition(Coordinate(0, 0), Coordinate(0, 0)))
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(codeBlock)
        assertEquals("let name: string = \"micaela\";\nlet num: number = 2;\n", result)
    }

    @Test
    fun test028_formatASimpleCodeBlockWithOtherSetOfRules() {
        val node1 =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val nodeList = listOf(node1, node2)
        val codeBlock: AstNode = CodeBlock(nodeList, TokenPosition(Coordinate(0, 0), Coordinate(0, 0)))
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(codeBlock)
        assertEquals("let name:string=\"micaela\";\nlet num:number=2;\n", result)
    }

    @Test
    fun test029_formatALargeCodeBlock() {
        val node1 =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node3 =
            VariableDeclarationNode(
                TokenType.LET,
                "sum",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node4 =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node5 =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node6 =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
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
        val nodeList = listOf(node1, node2, node3, node4, node5, node6)
        val codeBlock: AstNode = CodeBlock(nodeList, TokenPosition(Coordinate(0, 0), Coordinate(0, 0)))
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(codeBlock)
        assertEquals(
            "let name: string = \"micaela\";\nlet num: number = 2;\nlet sum: number = 2 + 2;\n\nprintln(1);\nname = \"micaela\";\n" +
                "let result: number = 2 + (2 * 3);\n",
            result,
        )
    }

    @Test
    fun test030_formatALargeCodeBlockWithOtherSetOfRules() {
        val node1 =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node3 =
            VariableDeclarationNode(
                TokenType.LET,
                "sum",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node4 =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node5 =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node6 =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
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
        val nodeList = listOf(node1, node2, node3, node4, node5, node6)
        val codeBlock: AstNode = CodeBlock(nodeList, TokenPosition(Coordinate(0, 0), Coordinate(0, 0)))
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(codeBlock)
        assertEquals(
            "let name:string=\"micaela\";\nlet num:number=2;\nlet sum:number=2 + 2;\n\n\nprintln(1);\nname=\"micaela\";\n" +
                "let result:number=2 + (2 * 3);\n",
            result,
        )
    }

    @Test
    fun test031_formatASimpleIfOperation() {
        val node1 =
            AssignmentNode(
                "name",
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            PrintNode(
                LiteralNode("False", TokenType.BOOLEANLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list1 = listOf(node1)
        val list2 = listOf(node2)
        val node =
            IfNode(
                LiteralNode("True", TokenType.BOOLEANLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                CodeBlock(
                    list1,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("if (True) {\n\tname = \"micaela\";\n\t\n} else {\n\t\n\tprintln(False);\n\t\n}\n", result)
    }

    @Test
    fun test032_formatASimpleIfOperationWithOtherSetOfRules() {
        val node1 =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list1 = listOf(node1)
        val list2 = listOf(node2)
        val node =
            IfNode(
                LiteralNode(
                    "True",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list1,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals(
            "if (True) {\n\t\tname=\"micaela\";\n\t\t\n} else {\n\t\t" +
                "\n\t\t\n\t\tprintln(False);\n\t\t\n}\n",
            result,
        )
    }

    @Test
    fun test033_formatALargeThenAndElseIf() {
        val node1 =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node3 =
            VariableDeclarationNode(
                TokenType.LET,
                "sum",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node4 =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node5 =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node6 =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
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
        val node7 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node8 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node9 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list1 = listOf(node1, node2, node3, node4, node5, node6)
        val list2 = listOf(node7, node8, node9)
        val node =
            IfNode(
                LiteralNode(
                    "True",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list1,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals(
            "if (True) {\n\tlet name: string = \"micaela\";\n\tlet num: number = 2;\n\tlet sum: number = 2 + 2;" + "\n" +
                "\t\n" +
                "\tprintln(1);\n" +
                "\tname = \"micaela\";\n" +
                "\tlet result: number = 2 + (2 * 3);\n" +
                "\t\n" +
                "} else {\n" +
                "\t\n" +
                "\tprintln(False);\n" +
                "\t\n" +
                "\tprintln(False);\n" +
                "\t\n" +
                "\tprintln(False);\n" +
                "\t\n" +
                "}\n",
            result,
        )
    }

    @Test
    fun test034_formatALargeThenAndElseIfWithOtherSetOfRules() {
        val node1 =
            VariableDeclarationNode(
                TokenType.LET,
                "name",
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            VariableDeclarationNode(
                TokenType.LET,
                "num",
                TokenType.NUMBERTYPE,
                LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node3 =
            VariableDeclarationNode(
                TokenType.LET,
                "sum",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    LiteralNode("2", TokenType.NUMBERLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                    TokenType.PLUS,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node4 =
            PrintNode(
                LiteralNode(
                    "1",
                    TokenType.NUMBERLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node5 =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node6 =
            VariableDeclarationNode(
                TokenType.LET,
                "result",
                TokenType.NUMBERTYPE,
                BinaryOperationNode(
                    LiteralNode(
                        "2",
                        TokenType.NUMBERLITERAL,
                        TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                    ),
                    BinaryOperationNode(
                        LiteralNode(
                            "2",
                            TokenType.NUMBERLITERAL,
                            TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                        ),
                        LiteralNode(
                            "3",
                            TokenType.NUMBERLITERAL,
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
        val node7 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node8 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node9 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list1 = listOf(node1, node2, node3, node4, node5, node6)
        val list2 = listOf(node7, node8, node9)
        val node =
            IfNode(
                LiteralNode(
                    "True",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list1,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals(
            "if (True) {\n\t\tlet name:string=\"micaela\";\n\t\tlet num:number=2;\n\t\tlet sum:number=2 + 2;" + "\n" +
                "\t\t\n" + "\t\t\n" +
                "\t\tprintln(1);\n" +
                "\t\tname=\"micaela\";\n" +
                "\t\tlet result:number=2 + (2 * 3);\n" +
                "\t\t\n" +
                "} else {\n" +
                "\t\t\n" + "\t\t\n" +
                "\t\tprintln(False);\n" +
                "\t\t\n" + "\t\t\n" +
                "\t\tprintln(False);\n" +
                "\t\t\n" + "\t\t\n" +
                "\t\tprintln(False);\n" +
                "\t\t\n" +
                "}\n",
            result,
        )
    }

    @Test
    fun test035_formatAnIfInsideIf() {
        val node1 =
            AssignmentNode(
                "name",
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            PrintNode(
                LiteralNode("False", TokenType.BOOLEANLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list1 = listOf(node1)
        val list2 = listOf(node2)
        val ifNode =
            IfNode(
                LiteralNode("True", TokenType.BOOLEANLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                CodeBlock(
                    list1,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list3 = listOf(ifNode)
        val node =
            IfNode(
                LiteralNode("True", TokenType.BOOLEANLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                CodeBlock(
                    list3,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals(
            "if (True) {\n\tif (True) {\n\t\tname = \"micaela\";\n\t\t\n\t} else {\n\t\t\n\t\tprintln(False);\n\t\t\n\t}\n\t" +
                "\n} else {\n\t\n\tprintln(False);\n\t\n}\n",
            result,
        )
    }

    @Test
    fun test036_formatAnIfInsideIfWithOtherSetOfRules() {
        val node1 =
            AssignmentNode(
                "name",
                LiteralNode(
                    "micaela",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenType.STRINGTYPE,
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val node2 =
            PrintNode(
                LiteralNode(
                    "False",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list1 = listOf(node1)
        val list2 = listOf(node2)
        val ifNode =
            IfNode(
                LiteralNode(
                    "True",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list1,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val list3 = listOf(ifNode)
        val node =
            IfNode(
                LiteralNode(
                    "True",
                    TokenType.BOOLEANLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list3,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                CodeBlock(
                    list2,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath02)
        val result = formatter.format(node)
        assertEquals(
            "if (True) {\n" +
                "\t\tif (True) {\n" +
                "\t\t\t\tname=\"micaela\";\n" +
                "\t\t\t\t\n" +
                "\t\t} else {\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\tprintln(False);\n" +
                "\t\t\t\t\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "} else {\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\tprintln(False);\n" +
                "\t\t\n" +
                "}\n",
            result,
        )
    }

    @Test
    fun test037_formatASimpleReadEnvFunction() {
        val node =
            FunctionNode(
                TokenType.READENV,
                LiteralNode(
                    "env1",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("readEnv(\"env1\");\n", result)
    }

    @Test
    fun test038_formatASimpleReadInputFunction() {
        val node =
            FunctionNode(
                TokenType.READINPUT,
                LiteralNode(
                    "hello",
                    TokenType.STRINGLITERAL,
                    TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
                ),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        val formatter: Formatter = PrintScriptFormatter(formatterPath01)
        val result = formatter.format(node)
        assertEquals("readInput(\"hello\");\n", result)
    }
//    @Test
//    fun test037_formatASimpleReadInputFunction() {
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
