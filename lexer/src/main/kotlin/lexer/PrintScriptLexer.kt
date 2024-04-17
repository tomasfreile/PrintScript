@file:Suppress("ktlint:standard:no-wildcard-imports")

package lexer

import position.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import java.util.*
import java.util.regex.Pattern

class PrintScriptLexer(private val tokenMap: EnumMap<TokenType, Pattern>) : Lexer {
    // Pattern to match any token type
    private val tokenPattern: Pattern = tokenMap.values.joinToString("|").toRegex().toPattern()

    // Map of token types to their respective regex patterns
    private val typePatterns: Map<TokenType, Pattern> = tokenMap.entries.associate { (type, pattern) -> type to pattern }

    override fun lex(input: String): List<Token> {
        val tokens = ArrayList<Token>(input.length)
        var line = 0

        input.lines().forEach { lineContent ->
            tokens.addAll(getTokensByLine(lineContent, line))
            line++
        }

        return tokens
    }

    private fun getTokensByLine(
        input: String,
        line: Int,
    ): List<Token> {
        val tokens = ArrayList<Token>()
        val matcher = tokenPattern.matcher(input)
        var currentIndex = 0

        while (matcher.find()) {
            val tokenValue = matcher.group()
            val type = findTokenType(tokenValue)
            val start = matcher.start()
            val end = matcher.end()

            checkIllegalCharsBetweenTokens(input, currentIndex, start, line)

            val token =
                if (type == TokenType.STRINGLITERAL) {
                    PrintScriptToken(
                        type,
                        tokenValue.substring(1, tokenValue.length - 1),
                        Coordinate(line, matcher.start() + 1),
                        Coordinate(line, matcher.end() - 1),
                    )
                } else {
                    PrintScriptToken(type, tokenValue, Coordinate(line, matcher.start()), Coordinate(line, matcher.end()))
                }
            tokens.add(token)
            currentIndex = end
        }

        checkIllegalCharsAfterLastToken(input, currentIndex, line)

        return tokens
    }

    // Function to find the TokenType of a token string
    private fun findTokenType(token: String): TokenType {
        val matchedEntry = typePatterns.entries.find { (_, pattern) -> pattern.matcher(token).matches() }
        return matchedEntry?.key ?: throw IllegalStateException("Token type not found for token: $token")
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
