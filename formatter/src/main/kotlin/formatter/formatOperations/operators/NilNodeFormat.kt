package formatter.formatOperations.operators

import ast.AstNode
import ast.NilNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation

class NilNodeFormat : FormatOperation {
    override fun canHandle(node: AstNode): Boolean {
        return node is NilNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        return ""
    }
}
