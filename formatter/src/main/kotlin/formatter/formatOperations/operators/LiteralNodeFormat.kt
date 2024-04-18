package formatter.formatOperations.operators

import ast.AstNode
import ast.LiteralNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation
import token.TokenType

class LiteralNodeFormat : FormatOperation {
    override fun canHandle(node: AstNode): Boolean {
        return node is LiteralNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as LiteralNode
        if (node.type == TokenType.STRINGLITERAL) {
            return "\"${node.value}\""
        }
        return node.value
    }
}
