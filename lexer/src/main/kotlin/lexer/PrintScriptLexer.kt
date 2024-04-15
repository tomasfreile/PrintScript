@file:Suppress("ktlint:standard:no-wildcard-imports")

package lexer

import position.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import java.util.*
import java.util.regex.Pattern

class PrintScriptLexer(private val tokenMap: EnumMap<TokenType, Pattern>) : Lexer {
    private val tokenPattern: Pattern = tokenMap.values.joinToString("|").toRegex().toPattern()

    override fun lex(input: String): List<Token> {
        val tokens = ArrayList<Token>(input.length)
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
        val matcher = tokenPattern.matcher(input)
        var currentIndex = 0 // Track current index in the input string

        while (matcher.find()) {
            val token = matcher.group()
            val type = types[tokenMap.values.indexOfFirst { it.matcher(token).matches() }]
            val start = matcher.start()
            val end = matcher.end()

            // Check if there are any characters between the current token and the previous one
            checkIllegalCharsBetweenTokens(input, currentIndex, start, line)

            if (type == TokenType.STRING_LITERAL) {
                tokens.add(
                    PrintScriptToken(type, token.substring(1, token.length - 1), Coordinate(line, start + 1), Coordinate(line, end - 1)),
                )
            } else {
                tokens.add(PrintScriptToken(type, token, Coordinate(line, start), Coordinate(line, end)))
            }
            currentIndex = end
        }

        // Check for invalid characters after the last token
        checkIllegalCharsAfterLastToken(input, currentIndex, line)

        return tokens
    }

    private fun checkIllegalCharsAfterLastToken(
        input: String,
        currentIndex: Int,
        line: Int,
    ) {
        val remainingInvalidCharIndex = input.substring(currentIndex).indexOfFirst { !it.isWhitespace() }
        if (remainingInvalidCharIndex != -1) {
            val remainingInvalidCharPosition = currentIndex + remainingInvalidCharIndex
            throw IllegalArgumentException(
                "Invalid character '${input[remainingInvalidCharPosition]}' at line $line, position $remainingInvalidCharPosition",
            )
        }
    }

    private fun checkIllegalCharsBetweenTokens(
        input: String,
        currentIndex: Int,
        start: Int,
        line: Int,
    ) {
        val invalidCharIndex = input.substring(currentIndex, start).indexOfFirst { !it.isWhitespace() }
        if (invalidCharIndex != -1) {
            val invalidCharPosition = currentIndex + invalidCharIndex
            throw IllegalArgumentException(
                "Invalid character '${input[invalidCharPosition]}' at line $line, position $invalidCharPosition",
            )
        }
    }
}
