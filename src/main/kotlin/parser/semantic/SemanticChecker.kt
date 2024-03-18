package org.example.parser.semantic

import org.example.token.Token

interface SemanticChecker {
    fun checkSemantic(tokenList: List<Token>): Boolean
}