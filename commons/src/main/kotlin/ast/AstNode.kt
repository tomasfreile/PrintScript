package ast

import position.Coordinate
import position.TokenPosition
import token.TokenType

sealed class AstNode {
    abstract val position: TokenPosition
}

// Number, String, ValueIdentifier, Boolean
data class LiteralNode(val value: String, val type: TokenType, override val position: TokenPosition) : AstNode()

// Binary operations like +, -, *, /
data class BinaryOperationNode(
    val left: AstNode,
    val right: AstNode,
    val operator: TokenType,
    override val position: TokenPosition,
) : AstNode()

// Print statement
data class PrintNode(val expression: AstNode, override val position: TokenPosition) : AstNode()

// Variable declaration statement
data class VariableDeclarationNode(
    val declarationType: TokenType,
    val identifier: String,
    val valueType: TokenType,
    val expression: AstNode,
    override val position: TokenPosition,
) : AstNode()

// Assignment statement
data class AssignmentNode(val identifier: String, val expression: AstNode, override val position: TokenPosition) : AstNode()

// Code
data class CodeBlock(val nodes: List<AstNode>, override val position: TokenPosition) : AstNode()

// If statement
data class IfNode(
    val condition: LiteralNode,
    val thenBlock: CodeBlock,
    val elseBlock: CodeBlock,
    override val position: TokenPosition,
) : AstNode()

// Function
data class FunctionNode(val function: TokenType, val expression: AstNode, override val position: TokenPosition) : AstNode()

// Nil
data object NilNode : AstNode() {
    override val position: TokenPosition
        get() = TokenPosition(Coordinate(0, 0), Coordinate(0, 0))
}
