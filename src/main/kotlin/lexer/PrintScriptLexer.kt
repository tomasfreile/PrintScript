package org.example.lexer

import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TypeEnum
import java.util.*
import java.util.regex.Pattern

class PrintScriptLexer(val tokenMap: EnumMap<TypeEnum, Pattern>) {
    fun lex(input: String): List<Token>{
        val tokens = ArrayList<Token>()
        val tokenTypes = tokenMap.keys.toList()
        var line = 0

        input.lines().forEach {
            tokens.addAll(getTokensByLine(it, tokenTypes, line))
            line++
        }

        return tokens
    }

    private fun getTokensByLine(input: String, types: List<TypeEnum>, line: Int): List<Token>{
        val tokens = ArrayList<Token>()
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