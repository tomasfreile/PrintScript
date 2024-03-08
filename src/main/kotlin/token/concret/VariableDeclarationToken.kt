package org.example.token.concret

import org.example.token.Coordinate
import org.example.token.Token
import org.example.token.TypeEnum

class VariableDeclarationToken(
    override val type: TypeEnum = TypeEnum.VARIABLE_KEYWORD,
    override val value: String = "let",
    override val start: Coordinate,
    override val end: Coordinate
) : Token {
}