package org.example.parser

import org.example.token.Token

interface Parse {
    fun parse(tokenList: List<Token>): Node
}