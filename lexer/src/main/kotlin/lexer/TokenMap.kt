package lexer


import token.TokenType
import java.util.*
import java.util.regex.Pattern

fun getTokenMap(): EnumMap<TokenType, Pattern>{
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
    tokenMap[TokenType.PRINT] = Pattern.compile("println")
    tokenMap[TokenType.IF] = Pattern.compile("if")
    tokenMap[TokenType.ELSE] = Pattern.compile("else")
    tokenMap[TokenType.NUMBER_TYPE] = Pattern.compile("Number")
    tokenMap[TokenType.STRING_TYPE] = Pattern.compile("String")
    tokenMap[TokenType.VARIABLE_KEYWORD] = Pattern.compile("let")

    // Literals
    tokenMap[TokenType.VALUE_IDENTIFIER] = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*")
    tokenMap[TokenType.STRING] = Pattern.compile("\'[^']*\'|\"[^\"]*\"")
    tokenMap[TokenType.NUMBER] = Pattern.compile("[0-9]+")

    return tokenMap
}