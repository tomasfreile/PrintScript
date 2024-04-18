package formatter.formatOperations

import ast.AstNode
import formatter.Formatter

interface FormatOperation {
    fun canHandle(node: AstNode): Boolean

    fun format(
        node: AstNode,
        formatter: Formatter,
    ): String
}
