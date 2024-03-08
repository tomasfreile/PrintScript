package org.example.token.concret

import org.example.token.Coordinate
import org.example.token.Token
import org.example.token.TypeEnum

class NumberTypeToken(
    override val type: TypeEnum = TypeEnum.NUMBER_TYPE,
    override val value: String = "Number",
    override val start: Coordinate,
    override val end: Coordinate
) : Token {
}