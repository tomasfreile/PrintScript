package org.example.token

interface Token {
    val type : TypeEnum
    val value : String
    val start: Coordinate
    val end: Coordinate
}