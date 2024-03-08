package org.example.token.concret

import org.example.token.Coordinate
import org.example.token.Token
import org.example.token.TypeEnum

class StringTypeToken(
    override val type: TypeEnum = TypeEnum.STRING_TYPE,
    override val value: String = "String",
    override val start: Coordinate,
    override val end: Coordinate
) : Token {
}