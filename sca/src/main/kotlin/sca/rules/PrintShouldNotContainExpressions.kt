package sca.rules

import ast.AstNode
import ast.LiteralNode
import ast.PrintNode
import sca.StaticCodeAnalyzerResult

class PrintShouldNotContainExpressions : Rule {
    override fun validate(node: AstNode): StaticCodeAnalyzerResult {
        return when (node) {
            is PrintNode -> {
                when (node.expression) {
                    is LiteralNode -> StaticCodeAnalyzerResult.Ok
                    else -> StaticCodeAnalyzerResult.Error("Print statement should not contain expressions")
                }
            }

            else -> StaticCodeAnalyzerResult.Ok
        }
    }
}
