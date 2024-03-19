package utils

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node

fun printAST(ast: Node, depth: Int = 0) {
    // print
    //   |
    //   +
    //  / \
    // 1   2

    val indent = "  ".repeat(depth)
    when (ast) {
        is ASTSingleNode -> {
            println("$indent|_${ast.token.value}")
            ast.node?.let { printAST(it, depth + 1) }
        }
        is ASTBinaryNode -> {
            println("$indent|_${ast.token.value}")
            ast.left?.let { printAST(it, depth + 1) }
            ast.right?.let { printAST(it, depth + 1) }
        }
    }
}