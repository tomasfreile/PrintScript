package formatter.formatOperations.operators

import ast.AstNode
import ast.IfNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation

class IfNodeFormat : FormatOperation {
    override fun canHandle(node: AstNode): Boolean {
        return node is IfNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as IfNode
        val indentationAmount = formatter.getRules()["ifIndentation"] as Int
        val condition = formatter.format(node.condition)
        val thenBlock = applyIndentations(formatter.format(node.thenBlock), indentationAmount)
        val elseBlock = applyIndentations(formatter.format(node.elseBlock), indentationAmount)
        return "if ($condition) {\n$thenBlock\n} else {\n$elseBlock\n}\n"
    }

    private fun applyIndentations(
        result: String,
        indentations: Int,
    ): String {
        val indentationSpaces = "\t".repeat(indentations)
        val dividedResult = result.split("\n")

        return applyIndentationsLoop(dividedResult, indentationSpaces, "")
    }

    private fun applyIndentationsLoop(
        dividedResult: List<String>,
        indentations: String,
        result: String,
    ): String {
        if (dividedResult.isEmpty()) {
            return result
        } else if (dividedResult.size == 1) {
            return result + indentations + dividedResult[0]
        } else {
            val newResult = result + indentations + dividedResult[0] + "\n"
            return applyIndentationsLoop(dividedResult.drop(1), indentations, newResult)
        }
    }
}
