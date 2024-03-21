package parser

import ast.Node
import org.example.parser.PrintParse
import token.Token
import org.example.parser.semantic.SemanticChecker
import org.example.parser.sintactic.SintacticChecker
import org.example.parser.sintactic.declarative.isDeclarative
import parser.parse.AssignationParse
import parser.parse.DeclarationParse
import parser.sintactic.commons.HasInvalidOperator
import parser.sintactic.isAssignation
import parser.sintactic.isPrint

data class InvalidSyntax(override val message: String): Exception(message)
class Parser(
    val syntaxRules: List<SintacticChecker>? = null,
    val semanticRules: List<SemanticChecker>? = null
) {
    fun parse(tokenList: List<Token>): Node {
        if(HasInvalidOperator().checkSyntax(tokenList)) throw InvalidSyntax("Has an Invalid Operator")
        return when{
            isDeclarative().checkSyntax(tokenList) -> DeclarationParse().parse(tokenList)
            isPrint().checkSyntax(tokenList) -> PrintParse().parse(tokenList)
            isAssignation().checkSyntax(tokenList) -> AssignationParse().parse(tokenList)
            else ->  throw InvalidSyntax("Invalid TokenInput Syntax :(")
        }
    }

}