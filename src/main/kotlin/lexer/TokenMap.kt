package org.example.lexer

import org.example.token.TypeEnum
import java.util.*
import java.util.regex.Pattern

fun getTokenMap(): EnumMap<TypeEnum, Pattern>{
    val tokenMap = EnumMap<TypeEnum, Pattern>(TypeEnum::class.java)

    // Single-character tokens.
    tokenMap[TypeEnum.ASSIGNATION] = Pattern.compile("=")
    tokenMap[TypeEnum.LEFT_PAREN] = Pattern.compile("\\(")
    tokenMap[TypeEnum.RIGHT_PAREN] = Pattern.compile("\\)")
    tokenMap[TypeEnum.PLUS] = Pattern.compile("\\+")
    tokenMap[TypeEnum.MINUS] = Pattern.compile("-")
    tokenMap[TypeEnum.STAR] = Pattern.compile("\\*")
    tokenMap[TypeEnum.SLASH] = Pattern.compile("/")

    // Separators
    tokenMap[TypeEnum.SEMICOLON] = Pattern.compile(";")
    tokenMap[TypeEnum.COLON] = Pattern.compile(":")

    // Keywords
    tokenMap[TypeEnum.PRINT] = Pattern.compile("println")
    tokenMap[TypeEnum.IF] = Pattern.compile("if")
    tokenMap[TypeEnum.ELSE] = Pattern.compile("else")
    tokenMap[TypeEnum.NUMBER_TYPE] = Pattern.compile("Number")
    tokenMap[TypeEnum.STRING_TYPE] = Pattern.compile("String")
    tokenMap[TypeEnum.VARIABLE_KEYWORD] = Pattern.compile("let")

    // Literals
    tokenMap[TypeEnum.VALUE_IDENTIFIER] = Pattern.compile("[a-zA-Z_][a-zA-Z0-9_]*")
    tokenMap[TypeEnum.STRING] = Pattern.compile("\'[^']*\'|\"[^\"]*\"")
    tokenMap[TypeEnum.NUMBER] = Pattern.compile("[0-9]+")

    return tokenMap
}