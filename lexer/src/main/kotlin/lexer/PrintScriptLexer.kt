package lexer

import token.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import java.util.EnumMap
import java.util.regex.Pattern

class PrintScriptLexer(private val tokenMap: EnumMap<TokenType, Pattern>) : Lexer {
    override fun lex(input: String): List<Token> {
        val tokens = ArrayList<Token>()
        var line = 0

        input.lines().forEach {
            tokens.addAll(getTokensByLine(it, tokenMap, line))
            line++
        }

        return tokens
    }

    private fun getTokensByLine(
        input: String,
        tokenMap: EnumMap<TokenType, Pattern>,
        line: Int,
    ): List<Token> {
        val tokens = ArrayList<Token>()
        val types = tokenMap.keys.toList()
        val matcher = tokenMap.values.joinToString("|").toRegex().toPattern().matcher(input)
        var currentIndex = 0 // Track current index in the input string

        while (matcher.find()) {
            val token = matcher.group()
            val type = types[tokenMap.values.indexOfFirst { it.matcher(token).matches() }]
            val start = matcher.start()
            val end = matcher.end()

            // Check if there are any characters between the current token and the previous one
            val invalidCharIndex = input.substring(currentIndex, start).indexOfFirst { !it.isWhitespace() }
            if (invalidCharIndex != -1) {
                val invalidCharPosition = currentIndex + invalidCharIndex
                throw IllegalArgumentException(
                    "Invalid character '${input[invalidCharPosition]}' at line $line, position $invalidCharPosition",
                )
            }

            if (type == TokenType.STRING) {
                tokens.add(
                    PrintScriptToken(type, token.substring(1, token.length - 1), Coordinate(line, start + 1), Coordinate(line, end - 1)),
                )
            } else {
                tokens.add(PrintScriptToken(type, token, Coordinate(line, start), Coordinate(line, end)))
            }
            currentIndex = end
        }

        // Check for invalid characters after the last token
        val remainingInvalidCharIndex = input.substring(currentIndex).indexOfFirst { !it.isWhitespace() }
        if (remainingInvalidCharIndex != -1) {
            val remainingInvalidCharPosition = currentIndex + remainingInvalidCharIndex
            throw IllegalArgumentException(
                "Invalid character '${input[remainingInvalidCharPosition]}' at line $line, position $remainingInvalidCharPosition",
            )
        }

        return tokens
    }
}
