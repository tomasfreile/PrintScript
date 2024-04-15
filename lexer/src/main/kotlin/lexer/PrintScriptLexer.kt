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

        input.lines().forEach { lineContent ->
            tokens.addAll(getTokensByLine(lineContent, line, types))
            line++
        }

        return tokens
    }

    private fun getTokensByLine(
        input: String,
        line: Int,
        tokenTypes: List<TokenType>,
    ): List<Token> {
        val tokens = ArrayList<Token>()
        val matcher = TokenRegexMatcher(tokenMap.values.toList()).matcher(input)

        while (matcher.find()) {
            for (type in tokenTypes) {
                if (matcher.group(type.name) != null) {
                    val token = createToken(type, line, matcher)
                    tokens.add(token)
                    break
                }
            }
        }
        return tokens
    }

    private fun createToken(
        type: TokenType,
        line: Int,
        matcher: Matcher,
    ): Token {
        return if (type == TokenType.STRINGLITERAL) {
            PrintScriptToken(
                type,
                matcher.group().substring(1, matcher.group().length - 1),
                Coordinate(line, matcher.start() + 1),
                Coordinate(line, matcher.end() - 1),
            )
        } else {
            PrintScriptToken(type, matcher.group(), Coordinate(line, matcher.start()), Coordinate(line, matcher.end()))
        }
    }
}
