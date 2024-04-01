package parser

import ast.Node
import parser.parse.AssignationParse
import parser.parse.PrintParse
import parser.sintactic.IsAssignation
import parser.sintactic.IsDeclarative
import parser.sintactic.IsPrint
import parser.sintactic.commons.HasInvalidOperator
import parser.sintactic.commons.HasString
import parser.sintactic.commons.HasValidParen
import parser.sintactic.commons.IsConcatenation
import token.Token

data class InvalidSyntax(override val message: String) : Exception(message)

class Parser {
    fun parse(tokenList: List<Token>): Node {
        preConditions(tokenList)
        return when {
            IsDeclarative().checkSyntax(tokenList) || IsAssignation().checkSyntax(tokenList) -> AssignationParse().parse(tokenList)
            IsPrint().checkSyntax(tokenList) -> PrintParse().parse(tokenList)
            else -> throw InvalidSyntax("Invalid TokenInput Syntax :(")
        }
    }

    private fun preConditions(tokenList: List<Token>) {
        if (HasInvalidOperator().checkSyntax(tokenList)) throw InvalidSyntax("Has an Invalid Operator")
        if (!IsConcatenation().checkSyntax(tokenList) && HasString().checkSyntax(tokenList)) throw InvalidSyntax("String just concatenate")
        if (!HasValidParen().checkSyntax(tokenList)) throw InvalidSyntax("Has Invalid Paren")
    }
}
