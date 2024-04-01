package parser

import ast.Node
import parser.analysis.semantic.NumberTypeDeclarationContent
import parser.analysis.semantic.PrintContent
import parser.analysis.semantic.StringTypeDeclarationContent
import parser.analysis.sintactic.IsAssignation
import parser.analysis.sintactic.IsDeclarative
import parser.analysis.sintactic.IsPrint
import parser.analysis.sintactic.commons.HasInvalidOperator
import parser.analysis.sintactic.commons.HasString
import parser.analysis.sintactic.commons.HasValidParen
import parser.analysis.sintactic.commons.IsConcatenation
import parser.parse.AssignationParse
import parser.parse.PrintParse
import token.Token
import token.TokenType

data class InvalidSyntax(override val message: String) : Exception(message)

data class InvalidCommonSense(override val message: String) : Exception(message)

class Parser {
    fun parse(tokenList: List<Token>): Node {
        checkSyntax(tokenList)
        return when {
            IsDeclarative().checkSyntax(tokenList) -> {
                checkDeclaration(tokenList)
                AssignationParse().parse(tokenList)
            }
            IsPrint().checkSyntax(tokenList) -> {
                checkPrintSemantic(tokenList)
                PrintParse().parse(tokenList)
            }
            IsAssignation().checkSyntax(tokenList) -> AssignationParse().parse(tokenList)
            else -> throw InvalidCommonSense("")
        }
    }

    private fun checkSyntax(tokenList: List<Token>) {
        if (HasInvalidOperator().checkSyntax(tokenList)) throw InvalidSyntax("Has an Invalid Operator")
        if (!IsConcatenation().checkSyntax(tokenList) && HasString().checkSyntax(tokenList)) throw InvalidSyntax("String just concatenate")
        if (!HasValidParen().checkSyntax(tokenList)) throw InvalidSyntax("Has Invalid Paren")
    }

    private fun checkPrintSemantic(tokenList: List<Token>) {
        if (!PrintContent().checkSemantic(tokenList)) throw InvalidCommonSense("Invalid Print Content, should be String or Number")
    }

    private fun checkDeclaration(tokenList: List<Token>) {
        val dataType = tokenList[3]
        when (dataType.type) {
            TokenType.STRING_TYPE -> {
                if (!StringTypeDeclarationContent().checkSemantic(tokenList)) throw InvalidCommonSense("String type has strings")
            }
            TokenType.NUMBER_TYPE -> {
                if (!NumberTypeDeclarationContent().checkSemantic(tokenList)) throw InvalidCommonSense("Number type has numbers")
            }
            else -> return
        }
    }
}
