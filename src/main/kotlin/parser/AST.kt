package org.example.parser

import org.example.token.Token

data class ASTBinaryNode(val right: ASTBinaryNode?, val left: ASTBinaryNode?, val token: Token)
class AST(val head: ASTBinaryNode) {

    fun getRight(): ASTBinaryNode?{
        return head.right
    }
    fun getLeft(): ASTBinaryNode?{
        return head.left
    }
    fun getHead(): ASTBinaryNode? {
        return head
    }
}