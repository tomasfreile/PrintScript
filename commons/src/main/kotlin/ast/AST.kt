package ast

import token.Token

// ES UN NODO AST BINARIO PORQUE SOPORTA DOS OPERACIONES Y EL TOKEN
interface Node {
    val token: Token
}

data class ASTBinaryNode(val right: Node?, val left: Node?, override val token: Token) : Node

data class ASTSingleNode(val node: Node?, override val token: Token) : Node

class AST()
