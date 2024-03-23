package parser

import ast.Node
import parser.parse.AssignationParse
import parser.parse.DeclarationParse
import parser.parse.PrintParse
import parser.semantic.SemanticChecker
import parser.sintactic.IsAssignation
import parser.sintactic.IsDeclarative
import parser.sintactic.IsPrint
import parser.sintactic.SintacticChecker
import parser.sintactic.commons.HasInvalidOperator
import token.Token

data class InvalidSyntax(override val message: String) : Exception(message)

class Parser(
    val syntaxRules: List<SintacticChecker>? = null,
    val semanticRules: List<SemanticChecker>? = null,
) {
    fun parse(tokenList: List<Token>): Node {
        if (HasInvalidOperator().checkSyntax(tokenList)) throw InvalidSyntax("Has an Invalid Operator")
        return when {
            IsDeclarative().checkSyntax(tokenList) -> DeclarationParse().parse(tokenList)
            IsPrint().checkSyntax(tokenList) -> PrintParse().parse(tokenList)
            IsAssignation().checkSyntax(tokenList) -> AssignationParse().parse(tokenList)
            else -> throw InvalidSyntax("Invalid TokenInput Syntax :(")
        }
    }
}
