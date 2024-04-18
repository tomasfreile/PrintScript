@file:Suppress("ktlint:standard:filename")

package lexer

import token.TokenType
import java.util.regex.Matcher
import java.util.regex.Pattern

class TokenRegexMatcher(private val pattern: Pattern) {
    constructor(type: TokenType, regex: String) : this(Pattern.compile("(?<${type.name}>$regex)"))
    constructor(matchers: List<TokenRegexMatcher>) : this(Pattern.compile(matchers.joinToString("|") { it.pattern.toString() }))

    fun matcher(input: String): Matcher = pattern.matcher(input)
}
