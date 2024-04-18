@file:Suppress("ktlint:standard:no-wildcard-imports")

package sca.rules

import ast.*
import sca.StaticCodeAnalyzerResult
import token.TokenType

class ReadInputShouldNotBePartOfExpression : Rule {
    override fun validate(node: AstNode): StaticCodeAnalyzerResult {
        return when (node) {
            is VariableDeclarationNode -> checkDeclaration(node)
            is AssignmentNode -> checkAssignment(node)
            else -> StaticCodeAnalyzerResult.Ok
        }
    }

    private fun checkDeclaration(node: VariableDeclarationNode): StaticCodeAnalyzerResult {
        return when (node.expression) {
            is FunctionNode -> StaticCodeAnalyzerResult.Ok
            is BinaryOperationNode -> checkBinaryOperation(node.expression as BinaryOperationNode)
            else -> StaticCodeAnalyzerResult.Ok
        }
    }

    private fun checkAssignment(node: AssignmentNode): StaticCodeAnalyzerResult {
        return when (node.expression) {
            is FunctionNode -> StaticCodeAnalyzerResult.Ok
            is BinaryOperationNode -> checkBinaryOperation(node.expression as BinaryOperationNode)
            else -> StaticCodeAnalyzerResult.Ok
        }
    }

    private fun checkBinaryOperation(node: BinaryOperationNode): StaticCodeAnalyzerResult {
        // checks that there is no readInput in the binary operation
        if (node.left is FunctionNode && (node.left as FunctionNode).function == TokenType.READINPUT) {
            return StaticCodeAnalyzerResult.Error("ReadInput should not be part of an expression")
        } else if (node.right is FunctionNode && (node.right as FunctionNode).function == TokenType.READINPUT) {
            return StaticCodeAnalyzerResult.Error("ReadInput should not be part of an expression")
        } else if (node.left is BinaryOperationNode) {
            return checkBinaryOperation(node.left as BinaryOperationNode)
        } else if (node.right is BinaryOperationNode) {
            return checkBinaryOperation(node.right as BinaryOperationNode)
        }
        return StaticCodeAnalyzerResult.Ok
    }
}
