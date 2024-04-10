package lexer.factory

import lexer.Lexer
import lexer.PrintScriptLexer
import lexer.getTokenMapV10
import lexer.getTokenMapV11

class LexerBuilder {
    fun build(version: String): Lexer {
        return when (version) {
            "1.0" -> PrintScriptLexer(getTokenMapV10())
            "1.1" -> PrintScriptLexer(getTokenMapV11())
            else -> throw IllegalArgumentException("Version not found")
        }
    }
}
