package sca.rules

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import sca.StaticCodeAnalyzerResult
import token.TokenType

class PrintShouldNotContainExpressions : Rule {
    override fun validate(ast: Node): StaticCodeAnalyzerResult {
        val errorMessage = "Print statement should not contain expressions"
        if (ast is ASTSingleNode && ast.token.type == TokenType.PRINT ) {
            // If the node is a print statement
            if (containsExpressions(ast.node)) {
                return StaticCodeAnalyzerResult.Error(errorMessage + ". Position ${ast.token.start.string()}")
            }
        }
        return StaticCodeAnalyzerResult.Ok
    }

    private fun containsExpressions(node: Node?): Boolean {
        if (node == null) return false
        // Check if the node is a binary node
        if (node is ASTBinaryNode) {
            return true
        }
        // Recursively check children nodes
        if (node is ASTSingleNode) {
            return containsExpressions(node.node)
        }
        return false
    }
}