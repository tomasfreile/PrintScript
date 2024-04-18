package formatter

import ast.AssignmentNode
import ast.CodeBlock
import ast.FunctionNode
import ast.IfNode
import ast.LiteralNode
import ast.PrintNode
import ast.VariableDeclarationNode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import position.Coordinate
import position.TokenPosition
import token.TokenType

// in these tests I am going to test if the 1.1 features are not available to the 1.0 version
// in other words I am testing that they are correctly separated and that the builder is working correctly
class FormatterFeaturesAreSeparatedTest {
    private val formatterPath01 = "src/test/resources/formatterTest01.yaml"
    private val formatter1 = PrintScriptFormatterBuilder().build("1.0", formatterPath01)

    @Test
    fun test001_constShouldNotBeAvailable() {
        val node =
            VariableDeclarationNode(
                TokenType.CONST,
                "name",
                TokenType.STRINGTYPE,
                LiteralNode("micaela", TokenType.STRINGLITERAL, TokenPosition(Coordinate(0, 0), Coordinate(0, 0))),
                TokenPosition(Coordinate(0, 0), Coordinate(0, 0)),
            )
        assertThrows<UnsupportedOperationException> { formatter1.format(node) }
    }

    @Test
    fun test002_booleanShouldNotBeAvailable() {
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
        assertThrows<UnsupportedOperationException> { formatter1.format(node) }
    }

    @Test
    fun test003_ifShouldNotBeAvailable() {
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
        assertThrows<UnsupportedOperationException> { formatter1.format(node) }
    }

    @Test
    fun test004_functionShouldNotBeAvailable() {
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
        assertThrows<UnsupportedOperationException> { formatter1.format(node) }
    }
}
