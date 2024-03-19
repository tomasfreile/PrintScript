package sca.rules

import ast.Node

interface Rule {
    fun validate(ast : Node) : String?
}

