import org.junit.jupiter.api.Test
import token.TokenType

class AstTest {
    @Test
    fun declarationAst() {
        val ast =
            ast.VariableDeclarationNode(
                TokenType.LET,
                "a",
                TokenType.NUMBER_TYPE,
                ast.LiteralNode("10", TokenType.NUMBER_LITERAL),
            )
        println(ast)
    }

    @Test
    fun printAst() {
        val ast = ast.PrintNode(ast.LiteralNode("10", TokenType.NUMBER_LITERAL))
        println(ast)
    }

    @Test
    fun assignationAst() {
        val ast = ast.AssignmentNode("a", ast.LiteralNode("10", TokenType.NUMBER_LITERAL))
    }

    @Test
    fun ifAst() {
        val ast =
            ast.IfNode(
                ast.LiteralNode("true", TokenType.BOOLEAN_LITERAL),
                ast.PrintNode(ast.LiteralNode("10", TokenType.NUMBER_LITERAL)),
                ast.PrintNode(ast.LiteralNode("20", TokenType.NUMBER_LITERAL)),
            )
        println(ast)
    }

    @Test
    fun binaryOperationAst() {
        val ast =
            ast.BinaryOperationNode(
                ast.LiteralNode("10", TokenType.NUMBER_LITERAL),
                ast.LiteralNode("20", TokenType.NUMBER_LITERAL),
                TokenType.PLUS,
            )
        println(ast)
    }

    @Test
    fun nullDeclarationAst() {
        val ast = ast.VariableDeclarationNode(TokenType.LET, "a", TokenType.NUMBER_TYPE, ast.NilNode)
    }
}
