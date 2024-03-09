package org.example.token

class PrintScriptToken(
    override val type: TypeEnum,
    override val value: String,
    override val start: Coordinate,
    override val end: Coordinate
) : Token {
}