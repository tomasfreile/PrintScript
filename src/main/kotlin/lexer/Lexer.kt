package org.example.lexer

import org.example.token.Token

interface Lexer {
    fun lex(input: String): List<Token>

}