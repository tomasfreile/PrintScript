package org.example.token

enum class TypeEnum {
    // Single-character tokens.
    ASSIGNATION, // =
    LEFT_PAREN,
    RIGHT_PAREN,
    PLUS, // +
    MINUS, // -
    STAR,// *
    SLASH, // /
    SEMICOLON, // ;
    DOUBLE_DOT, //:

    // Keywords
    PRINT, // print
    IF, // if
    ELSE, // else

    // Literals
    VALUE_IDENTIFIER, //let VALUE_IDENTIFIER: STRING
    VARIABLE_KEYWORD, // VARIABLE_KEYWORD name: String let
    NUMBER_TYPE, // Number
    STRING_TYPE, // String
}
