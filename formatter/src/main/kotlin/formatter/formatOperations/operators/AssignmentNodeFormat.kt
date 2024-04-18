package formatter.formatOperations.operators

import ast.AssignmentNode
import ast.AstNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation
import formatter.formatOperations.commons.FileDataChecker
import formatter.formatOperations.commons.FormatSemicolon
import formatter.formatOperations.commons.HandleSpace

class AssignmentNodeFormat : FormatOperation {
    private val spaceHandler = HandleSpace()
    private val semicolonHandler = FormatSemicolon()
    private val fileDataChecker = FileDataChecker()

    override fun canHandle(node: AstNode): Boolean {
        return node is AssignmentNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as AssignmentNode
        val equalsBefore = fileDataChecker.checkBooleanData(formatter.getRules(), "assignationBefore")
        val equalsAfter = fileDataChecker.checkBooleanData(formatter.getRules(), "assignationAfter")
        val format = spaceHandler.handleSpaces("=", equalsBefore, equalsAfter, node.identifier) + formatter.format(node.expression)
        return semicolonHandler.formatSemicolon(format)
    }
}
