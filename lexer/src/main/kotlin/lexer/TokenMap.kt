package lexer

import token.TokenType
import java.util.EnumMap
import java.util.regex.Pattern

fun getTokenMapV10(): EnumMap<TokenType, Pattern> {
    val tokenMap = EnumMap<TokenType, Pattern>(TokenType::class.java)

    // Single-character tokens.
    tokenMap[TokenType.ASSIGNATION] = Pattern.compile("=")
    tokenMap[TokenType.LEFT_PAREN] = Pattern.compile("\\(")
    tokenMap[TokenType.RIGHT_PAREN] = Pattern.compile("\\)")
    tokenMap[TokenType.PLUS] = Pattern.compile("\\+")
    tokenMap[TokenType.MINUS] = Pattern.compile("-")
    tokenMap[TokenType.STAR] = Pattern.compile("\\*")
    tokenMap[TokenType.SLASH] = Pattern.compile("/")

    // Separators
    tokenMap[TokenType.SEMICOLON] = Pattern.compile(";")
    tokenMap[TokenType.COLON] = Pattern.compile(":")

    // Keywords
    tokenMap[TokenType.PRINT] = Pattern.compile("\\bprintln")
    tokenMap[TokenType.NUMBER_TYPE] = Pattern.compile("\\bnumber\\b")
    tokenMap[TokenType.STRING_TYPE] = Pattern.compile("\\bstring\\b")
    tokenMap[TokenType.LET] = Pattern.compile("\\blet\\b")

    // Literals
    tokenMap[TokenType.VALUE_IDENTIFIER_LITERAL] = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*")
    tokenMap[TokenType.STRING_LITERAL] = Pattern.compile("\'[^']*\'|\"[^\"]*\"")
    tokenMap[TokenType.NUMBER_LITERAL] = Pattern.compile("[0-9]+")

    return tokenMap
}

fun getTokenMapV11(): EnumMap<TokenType, Pattern> {
    val tokenMap = getTokenMapV10()

    tokenMap[TokenType.BOOLEAN_TYPE] = Pattern.compile("\\bboolean\\b")
    tokenMap[TokenType.CONST] = Pattern.compile("\\bconst\\b")
    tokenMap[TokenType.READ_INPUT] = Pattern.compile("\\breadInput\\b")
    tokenMap[TokenType.READ_ENV] = Pattern.compile("\\breadEnv\\b")
    tokenMap[TokenType.BOOLEAN_LITERAL] = Pattern.compile("\\btrue\\b|\\bfalse\\b")
    tokenMap[TokenType.IF] = Pattern.compile("\\bif")
    tokenMap[TokenType.ELSE] = Pattern.compile("\\belse")
    tokenMap[TokenType.LEFT_BRACE] = Pattern.compile("\\{")
    tokenMap[TokenType.RIGHT_BRACE] = Pattern.compile("}")

    return tokenMap
}
