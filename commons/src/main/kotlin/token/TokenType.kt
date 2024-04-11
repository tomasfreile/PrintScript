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
    LEFT_BRACE, // {
    RIGHT_BRACE, // }

    // Separators
    SEMICOLON, // ;
    COLON, // :

    // Keywords
    PRINT, // print
    IF, // if
    ELSE, // else
    NUMBER_TYPE, // number
    STRING_TYPE, // string
    BOOLEAN_TYPE, // boolean

    LET, // let
    CONST, // const

    // Functions
    READ_INPUT,
    READ_ENV,

    // Literals
    STRING_LITERAL, // "hola"
    NUMBER_LITERAL, // 123
    BOOLEAN_LITERAL, // true or false
    VALUE_IDENTIFIER_LITERAL, // let VALUE_IDENTIFIER: STRING
}
