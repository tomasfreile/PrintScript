package org.example.sca.rules

import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.TokenType

interface Rule {
    fun validate(ast : Node) : String?
}

