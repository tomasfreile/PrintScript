package org.example.parser.sintactic

import org.example.token.Token

interface SintacticChecker {

    fun checkSyntax(tokenList: List<Token>): Boolean
}