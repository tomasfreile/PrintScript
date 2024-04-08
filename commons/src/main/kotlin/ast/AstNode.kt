package ast

import token.TokenType

sealed class AstNode

// Number, String, ValueIdentifier, Boolean
data class LiteralNode(val value: String, val type: TokenType) : AstNode()

// Binary operations like +, -, *, /
data class BinaryOperationNode(val left: AstNode, val right: AstNode, val operator: TokenType) : AstNode()

// Print statement
data class PrintNode(val expression: AstNode) : AstNode()

// Variable declaration statement
data class VariableDeclarationNode(
    val declarationType: TokenType,
    val identifier: String,
    val valueType: TokenType,
    val expression: AstNode,
) : AstNode()

// Assignment statement
data class AssignmentNode(val identifier: String, val expression: AstNode) : AstNode()

// If statement
data class IfNode(val condition: LiteralNode, val thenBlock: AstNode, val elseBlock: AstNode) : AstNode()

// Nil
data object NilNode : AstNode()
