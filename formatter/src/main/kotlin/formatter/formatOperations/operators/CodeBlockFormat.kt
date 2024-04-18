package formatter.formatOperations.operators

import ast.AstNode
import ast.CodeBlock
import formatter.Formatter
import formatter.formatOperations.FormatOperation

class CodeBlockFormat : FormatOperation {
    override fun canHandle(node: AstNode): Boolean {
        return node is CodeBlock
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as CodeBlock
        return formatCodeBlockLoop(node, formatter, "")
    }

    private fun formatCodeBlockLoop(
        node: CodeBlock,
        formatter: Formatter,
        result: String,
    ): String {
        return if (node.nodes.isEmpty()) {
            result
        } else {
            val newResult = result + formatter.format(node.nodes[0])
            formatCodeBlockLoop(CodeBlock(node.nodes.drop(1), node.position), formatter, newResult)
        }
    }
}
