package org.example.lexer

import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TokenType
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class PrintScriptLexer(private val tokenMap: EnumMap<TokenType, Pattern>) {
    fun lex(input: String): List<Token>{
        val tokens = ArrayList<Token>()
        var line = 0

        input.lines().forEach {
            tokens.addAll(getTokensByLine(it, tokenMap, line))
            line++
        }

        return tokens
    }

    private fun getTokensByLine(input: String, tokenMap: EnumMap<TokenType, Pattern>, line: Int): List<Token>{
        val tokens = ArrayList<Token>()
        val types = tokenMap.keys.toList()
        val matcher = tokenMap.values.joinToString("|").toRegex().toPattern().matcher(input)
        while (matcher.find()){
            val token = matcher.group()
            val type = types[tokenMap.values.indexOfFirst { it.matcher(token).matches() }]
            val start = matcher.start()
            val end = matcher.end()
            tokens.add(PrintScriptToken(type, token, Coordinate(line, start), Coordinate(line, end)))
        }
        return tokens
    }

}