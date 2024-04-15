package token

enum class TokenType {
    // Single-character tokens.
    ASSIGNATION, // =
    LEFTPAREN,
    RIGHTPAREN,
    PLUS, // +
    MINUS, // -
    STAR, // *
    SLASH, // /
    LEFTBRACE, // {
    RIGHTBRACE, // }

    // Separators
    SEMICOLON, // ;
    COLON, // :

    // Keywords
    PRINT, // print
    IF, // if
    ELSE, // else
    NUMBERTYPE, // number
    STRINGTYPE, // string
    BOOLEANTYPE, // boolean

    LET, // let
    CONST, // const

    // Functions
    READINPUT,
    READENV,

    // Literals
    STRINGLITERAL, // "hola"
    NUMBERLITERAL, // 123
    BOOLEANLITERAL, // true or false
    VALUEIDENTIFIERLITERAL, // let VALUE_IDENTIFIER: STRING

    // Invalid
    INVALID,
}
