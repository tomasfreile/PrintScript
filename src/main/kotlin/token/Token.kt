package org.example.token

interface Token {
    val type : TokenType
    val value : String
    val start: Coordinate
    val end: Coordinate
}