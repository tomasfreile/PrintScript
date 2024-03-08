package org.example.token

enum class TypeEnum {

    // Operators
    PLUS, // +
    MINUS, // -
    STAR,// *
    SLASH, // /
    ASSIGNATION, // =

    // Separators
    SEMICOLON, // ;
    LEFT_PAREN, // (
    RIGHT_PAREN, // )
    COLON, // :

    // Keywords
    PRINT, // print
    IF, // if
    ELSE, // else
    NUMBER_TYPE, // Number
    STRING_TYPE, // String
    VARIABLE_KEYWORD, // VARIABLE_KEYWORD name: String

    // Literals
    VALUE_IDENTIFIER, //let VALUE_IDENTIFIER: STRING
    STRING, // "hola"
    NUMBER, // 123

}
