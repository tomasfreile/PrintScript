package formatter.formatOperations.operators

import ast.AstNode
import ast.PrintNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation
import formatter.formatOperations.commons.FileDataChecker
import formatter.formatOperations.commons.FormatSemicolon

class PrintNodeFormat : FormatOperation {
    private val semicolonHandler = FormatSemicolon()
    private val fileDataChecker = FileDataChecker()

    override fun canHandle(node: AstNode): Boolean {
        return node is PrintNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as PrintNode
        val printJump = fileDataChecker.checkNumberData(formatter.getRules(), "printJump")
        val format = "\n".repeat(printJump) + "println(" + formatter.format(node.expression) + ")"
        return semicolonHandler.formatSemicolon(format)
    }
}
