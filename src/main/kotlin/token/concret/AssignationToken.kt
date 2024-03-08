package org.example.token.concret

import org.example.token.Coordinate
import org.example.token.Token
import org.example.token.TypeEnum

class AssignationToken(
    override val type: TypeEnum = TypeEnum.ASSIGNATION,
    override val value: String = "=",
    override val start: Coordinate,
    override val end: Coordinate
) : Token {
}

