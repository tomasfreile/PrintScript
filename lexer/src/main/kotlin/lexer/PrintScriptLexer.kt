@file:Suppress("ktlint:standard:no-wildcard-imports")

package lexer

import position.Coordinate
import token.PrintScriptToken
import token.Token
import token.TokenType
import java.util.*
import java.util.regex.Matcher

class PrintScriptLexer(private val tokenMap: EnumMap<TokenType, TokenRegexMatcher>) : Lexer {
    override fun lex(input: String): List<Token> {
        val tokens = ArrayList<Token>()
        var line = 0
        val types = tokenMap.keys.toList()

        // For each line in the input
        input.lines().forEach { lineContent ->
            // Get the tokens for the current line and add them to the list
            tokens.addAll(getTokensByLine(lineContent, line, types))
            line++
        }

        return tokens
    }

    // Function to get tokens by line
    private fun getTokensByLine(
        input: String,
        line: Int,
        tokenTypes: List<TokenType>,
    ): List<Token> {
        val tokens = ArrayList<Token>()
        // Create a matcher for the input string
        val matcher = TokenRegexMatcher(tokenMap.values.toList()).matcher(input)

        // While there are tokens to match
        while (matcher.find()) {
            // Find the token type that matches the current token
            for (type in tokenTypes) {
                // If the token type matches the current token
                if (matcher.group(type.name) != null) {
                    // Create the token and add it to the list
                    val token = createToken(type, line, matcher)
                    tokens.add(token)
                    break
                }
            }
        }
        return tokens
    }

    // Function to create a token
    private fun createToken(
        type: TokenType,
        line: Int,
        matcher: Matcher,
    ): Token {
        return if (type == TokenType.STRINGLITERAL) {
            // Create string token
            PrintScriptToken(
                type,
                matcher.group().substring(1, matcher.group().length - 1),
                Coordinate(line, matcher.start() + 1),
                Coordinate(line, matcher.end() - 1),
            )
        } else {
            // Create regular token
            PrintScriptToken(type, matcher.group(), Coordinate(line, matcher.start()), Coordinate(line, matcher.end()))
        }
    }
}
