package formatter.formatOperations.operators

import ast.AstNode
import ast.BinaryOperationNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation
import token.TokenType

class BinaryOperationNodeFormat : FormatOperation {
    override fun canHandle(node: AstNode): Boolean {
        return node is BinaryOperationNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as BinaryOperationNode
        val left = formatter.format(node.left)
        val right = formatter.format(node.right)
        val operator = defineOperatorSymbol(node.operator)
        return if (node.left is BinaryOperationNode && node.right is BinaryOperationNode) {
            "($left) $operator ($right)"
        } else if (node.left is BinaryOperationNode) {
            "($left) $operator $right"
        } else if (node.right is BinaryOperationNode) {
            "$left $operator ($right)"
        } else {
            "$left $operator $right"
        }
    }

    private fun defineOperatorSymbol(operator: TokenType): String {
        return when (operator) {
            TokenType.PLUS -> "+"
            TokenType.MINUS -> "-"
            TokenType.STAR -> "*"
            TokenType.SLASH -> "/"
            else -> ""
        }
    }
}
