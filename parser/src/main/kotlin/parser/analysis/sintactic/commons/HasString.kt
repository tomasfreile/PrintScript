package parser.analysis.sintactic.commons

import parser.analysis.sintactic.IsAssignation
import parser.analysis.sintactic.IsDeclarative
import parser.analysis.sintactic.IsPrint
import parser.analysis.sintactic.SintacticChecker
import parser.utils.Type
import token.Token
import token.TokenType

class HasString : SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        val type = checkParseType(tokenList)
        return when (type) {
            Type.ASSIGNATION -> checkContent(tokenList, 2, tokenList.size - 1) // content position depends on the type of parse
            Type.PRINT -> checkContent(tokenList, 2, tokenList.size - 2)
            Type.DECLARATION -> checkContent(tokenList, 5, tokenList.size - 1)
            Type.NIL -> false
        }
    }

    private fun checkContent(
        tokenList: List<Token>,
        from: Int,
        to: Int,
    ): Boolean {
        for (token in tokenList.subList(from, to)) {
            when (token.type) {
                TokenType.STRING -> return true
                else -> continue
            }
        }
        return false
    }

    private fun checkParseType(tokenList: List<Token>): Type {
        return when {
            IsDeclarative().checkSyntax(tokenList) -> Type.DECLARATION
            IsPrint().checkSyntax(tokenList) -> Type.PRINT
            IsAssignation().checkSyntax(tokenList) -> Type.ASSIGNATION
            else -> Type.NIL
        }
    }
}
