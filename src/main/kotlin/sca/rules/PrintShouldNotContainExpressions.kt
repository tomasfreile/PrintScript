package org.example.sca.rules

import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.TokenType

class PrintShouldNotContainExpressions : Rule {
    override fun validate(ast: Node): String? {
        val errorMessage = "Print statement should not contain expressions"
        if (ast is ASTSingleNode && ast.token.type == TokenType.PRINT ) {
            // If the node is a print statement
            if (containsExpressions(ast.node)) {
                return errorMessage
            }
        }
        return null
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