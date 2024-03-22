package lexer

import token.Token

interface Lexer {
    fun lex(input: String): List<Token>
}
