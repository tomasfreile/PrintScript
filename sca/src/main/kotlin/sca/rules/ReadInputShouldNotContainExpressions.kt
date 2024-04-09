package sca.rules

import ast.AstNode
import ast.FunctionNode
import ast.LiteralNode
import ast.VariableDeclarationNode
import sca.StaticCodeAnalyzerResult

class ReadInputShouldNotContainExpressions : Rule {
    override fun validate(node: AstNode): StaticCodeAnalyzerResult {
        return when (node) {
            is VariableDeclarationNode -> {
                when (node.expression) {
                    is FunctionNode -> {
                        if ((node.expression as FunctionNode).expression is LiteralNode) {
                            StaticCodeAnalyzerResult.Ok
                        } else {
                            StaticCodeAnalyzerResult.Error("Read input statement should not contain expressions")
                        }
                    }
                    else -> StaticCodeAnalyzerResult.Ok
                }
            }
            else -> StaticCodeAnalyzerResult.Ok
        }
    }
}
