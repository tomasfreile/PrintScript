@file:Suppress("ktlint:standard:filename")

package lexer

import token.TokenType
import java.util.regex.Matcher
import java.util.regex.Pattern

// Class to match a regex pattern with a token type
class TokenRegexMatcher(private val pattern: Pattern) {
    // Constructor for creating a regex pattern for a single token type
    constructor(type: TokenType, regex: String) : this(
        Pattern.compile(String.format("(?<%s>%s)", type.name, regex)),
    )

    // Constructor for creating a regex pattern for multiple token types
    constructor(matchers: List<TokenRegexMatcher>) : this(
        Pattern.compile(matchers.joinToString("|") { it.pattern.toString() }),
    )

    // Function to match the input string with the regex pattern
    fun matcher(input: String): Matcher = pattern.matcher(input)
}
