package token

enum class TokenType {
    // Single-character tokens.
    ASSIGNATION, // =
    LEFT_PAREN,
    RIGHT_PAREN,
    PLUS, // +
    MINUS, // -
    STAR, // *
    SLASH, // /

    // Separators
    SEMICOLON, // ;
    COLON, // :

    // Keywords
    PRINT, // print
    IF, // if
    ELSE, // else
    NUMBER_TYPE, // Number
    STRING_TYPE, // String
    VARIABLE_KEYWORD, // VARIABLE_KEYWORD name: String

    // Literals
    VALUE_IDENTIFIER, // let VALUE_IDENTIFIER: STRING
    STRING, // "hola"
    NUMBER, // 123
}
