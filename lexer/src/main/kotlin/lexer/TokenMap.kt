@file:Suppress("ktlint:standard:no-wildcard-imports")

package lexer

import token.TokenType
import java.util.*

fun getTokenMapV10(): EnumMap<TokenType, TokenRegexMatcher> {
    val tokenMap = EnumMap<TokenType, TokenRegexMatcher>(TokenType::class.java)

    // Single-character tokens.
    tokenMap[TokenType.ASSIGNATION] = TokenRegexMatcher(TokenType.ASSIGNATION, "=")
    tokenMap[TokenType.LEFTPAREN] = TokenRegexMatcher(TokenType.LEFTPAREN, "\\(")
    tokenMap[TokenType.RIGHTPAREN] = TokenRegexMatcher(TokenType.RIGHTPAREN, "\\)")
    tokenMap[TokenType.PLUS] = TokenRegexMatcher(TokenType.PLUS, "\\+")
    tokenMap[TokenType.MINUS] = TokenRegexMatcher(TokenType.MINUS, "-")
    tokenMap[TokenType.STAR] = TokenRegexMatcher(TokenType.STAR, "\\*")
    tokenMap[TokenType.SLASH] = TokenRegexMatcher(TokenType.SLASH, "/")

    // Separators
    tokenMap[TokenType.SEMICOLON] = TokenRegexMatcher(TokenType.SEMICOLON, ";")
    tokenMap[TokenType.COLON] = TokenRegexMatcher(TokenType.COLON, ":")

    // Keywords
    tokenMap[TokenType.PRINT] = TokenRegexMatcher(TokenType.PRINT, "\\bprintln")
    tokenMap[TokenType.NUMBERTYPE] = TokenRegexMatcher(TokenType.NUMBERTYPE, "(?:)number(\\b|;)")
    tokenMap[TokenType.STRINGTYPE] = TokenRegexMatcher(TokenType.STRINGTYPE, "(?:)string(\\b|;)")
    tokenMap[TokenType.LET] = TokenRegexMatcher(TokenType.LET, "\\blet\\b")

    // Literals
    tokenMap[TokenType.STRINGLITERAL] = TokenRegexMatcher(TokenType.STRINGLITERAL, "\'[^\']*\'|\"[^\"]*\"")
    tokenMap[TokenType.NUMBERLITERAL] = TokenRegexMatcher(TokenType.NUMBERLITERAL, "[0-9]+(\\.[0-9]+)?")
    tokenMap[TokenType.VALUEIDENTIFIERLITERAL] = TokenRegexMatcher(TokenType.VALUEIDENTIFIERLITERAL, "[a-zA-Z_][a-zA-Z0-9_]*")

    // Invalid
    tokenMap[TokenType.INVALID] = TokenRegexMatcher(TokenType.INVALID, "[^ \\n\\t]")

    return tokenMap
}

fun getTokenMapV11(): EnumMap<TokenType, TokenRegexMatcher> {
    val tokenMap = getTokenMapV10()

    tokenMap[TokenType.BOOLEANTYPE] = TokenRegexMatcher(TokenType.BOOLEANTYPE, "(?:)boolean(\\b|;)")
    tokenMap[TokenType.CONST] = TokenRegexMatcher(TokenType.CONST, "\\bconst\\b")
    tokenMap[TokenType.READINPUT] = TokenRegexMatcher(TokenType.READINPUT, "\\breadInput")
    tokenMap[TokenType.READENV] = TokenRegexMatcher(TokenType.READENV, "\\breadEnv")
    tokenMap[TokenType.BOOLEANLITERAL] = TokenRegexMatcher(TokenType.BOOLEANLITERAL, "(?:true|false)")
    tokenMap[TokenType.IF] = TokenRegexMatcher(TokenType.IF, "\\bif\\b")
    tokenMap[TokenType.ELSE] = TokenRegexMatcher(TokenType.ELSE, "\\belse\\b")
    tokenMap[TokenType.LEFTBRACE] = TokenRegexMatcher(TokenType.LEFTBRACE, "\\{")
    tokenMap[TokenType.RIGHTBRACE] = TokenRegexMatcher(TokenType.RIGHTBRACE, "\\}")

    return tokenMap
}
