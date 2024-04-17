@file:Suppress("ktlint:standard:no-wildcard-imports")

package lexer

import token.TokenType
import java.util.*
import java.util.regex.Pattern

fun getTokenMapV10(): EnumMap<TokenType, Pattern> {
    val tokenMap = EnumMap<TokenType, Pattern>(TokenType::class.java)

    // Single-character tokens.
    tokenMap[TokenType.ASSIGNATION] = Pattern.compile("=")
    tokenMap[TokenType.LEFTPAREN] = Pattern.compile("\\(")
    tokenMap[TokenType.RIGHTPAREN] = Pattern.compile("\\)")
    tokenMap[TokenType.PLUS] = Pattern.compile("\\+")
    tokenMap[TokenType.MINUS] = Pattern.compile("-")
    tokenMap[TokenType.STAR] = Pattern.compile("\\*")
    tokenMap[TokenType.SLASH] = Pattern.compile("/")

    // Separators
    tokenMap[TokenType.SEMICOLON] = Pattern.compile(";")
    tokenMap[TokenType.COLON] = Pattern.compile(":")

    // Keywords
    tokenMap[TokenType.PRINT] = Pattern.compile("\\bprintln")
    tokenMap[TokenType.NUMBERTYPE] = Pattern.compile("\\bnumber\\b")
    tokenMap[TokenType.STRINGTYPE] = Pattern.compile("\\bstring\\b")
    tokenMap[TokenType.LET] = Pattern.compile("\\blet\\b")

    // Literals
    tokenMap[TokenType.STRINGLITERAL] = Pattern.compile("\'[^']*\'|\"[^\"]*\"")
    tokenMap[TokenType.NUMBERLITERAL] = Pattern.compile("[0-9]+(\\.[0-9]+)?")
    tokenMap[TokenType.VALUEIDENTIFIERLITERAL] = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*")

    return tokenMap
}

fun getTokenMapV11(): EnumMap<TokenType, Pattern> {
    val tokenMap = getTokenMapV10()

    tokenMap[TokenType.BOOLEANTYPE] = Pattern.compile("\\bboolean\\b")
    tokenMap[TokenType.CONST] = Pattern.compile("\\bconst\\b")
    tokenMap[TokenType.READINPUT] = Pattern.compile("\\breadInput\\b")
    tokenMap[TokenType.READENV] = Pattern.compile("\\breadEnv\\b")
    tokenMap[TokenType.BOOLEANLITERAL] = Pattern.compile("\\btrue\\b|\\bfalse\\b")
    tokenMap[TokenType.IF] = Pattern.compile("\\bif\\b")
    tokenMap[TokenType.ELSE] = Pattern.compile("\\belse\\b")
    tokenMap[TokenType.LEFTBRACE] = Pattern.compile("\\{")
    tokenMap[TokenType.RIGHTBRACE] = Pattern.compile("}")

    return tokenMap
}
