package org.example.token

enum class TypeEnum {

    VARIABLE_KEYWORD, // VARIABLE_KEYWORD name: String
    ASSIGNATION, // =

    // Single-character tokens.
    LEFT_PAREN,
    RIGHT_PAREN,
    PLUS, // +
    MINUS, // -
    STAR,// *
    SLASH, // /
    SEMICOLON, // ;

    // Keywords
    PRINT, // print
    IF, // if
    ELSE, // else

    // Literals
    VALUE_IDENTIFIER, //let VALUE_IDENTIFIER: STRING
    NUMBER,
    STRING,


}