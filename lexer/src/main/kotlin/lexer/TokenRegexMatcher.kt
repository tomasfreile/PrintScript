@file:Suppress("ktlint:standard:filename")

package lexer

import token.TokenType
import java.util.regex.Matcher
import java.util.regex.Pattern

class TokenRegexMatcher {
    private val pattern: Pattern

    constructor(type: TokenType, regex: String) {
        this.pattern = Pattern.compile(String.format("(?<%s>%s)", type.name, regex))
    }

    constructor(matchers: List<TokenRegexMatcher>) {
        val regex = matchers.joinToString("|") { it.pattern.toString() }
        this.pattern = Pattern.compile(regex)
    }

    fun matcher(input: String): Matcher {
        return pattern.matcher(input)
    }
}
