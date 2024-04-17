@file:Suppress("ktlint:standard:no-wildcard-imports")

package sca.rules

import ast.*
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
                            StaticCodeAnalyzerResult.Error(
                                "Read input statement should not contain expressions. " +
                                    "Position: ${node.expression.position.start.string() + " - " + node.expression.position.end.string()}",
                            )
                        }
                    }
                    else -> StaticCodeAnalyzerResult.Ok
                }
            }
            is PrintNode -> {
                when (node.expression) {
                    is FunctionNode -> {
                        if ((node.expression as FunctionNode).expression is LiteralNode) {
                            StaticCodeAnalyzerResult.Ok
                        } else {
                            StaticCodeAnalyzerResult.Error(
                                "Read input statement should not contain expressions. " +
                                    "Position: ${node.expression.position.start.string() + " - " + node.expression.position.end.string()}",
                            )
                        }
                    }
                    else -> StaticCodeAnalyzerResult.Ok
                }
            }
            else -> StaticCodeAnalyzerResult.Ok
        }
    }
}
