package utils

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node

fun printAST(
    node: Node?,
    depth: Int = 0,
) {
    if (node == null) return

    val indentation = "  ".repeat(depth)
    val underline = "\u001B[4m"
    val reset = "\u001B[0m"

    when (node) {
        is ASTBinaryNode -> {
            println("$indentation ${underline}Binary Node:$reset ${node.token.value}")
            println("$indentation ${underline}Right:$reset")
            printAST(node.right, depth + 1)
            println("$indentation ${underline}Left:$reset")
            printAST(node.left, depth + 1)
        }
        is ASTSingleNode -> {
            println("$indentation Single Node: ${node.token.value}")
            printAST(node.node, depth + 1)
        }
    }
}
