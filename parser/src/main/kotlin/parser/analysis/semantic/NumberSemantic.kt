package parser.analysis.semantic

import parser.InvalidDataTypeException
import parser.analysis.semantic.common.HasZeroDivision
import parser.analysis.semantic.common.OperatorIsFormatted
import token.Token
import token.TokenType

class NumberSemantic : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        return isFormatted(tokenList) && hasNoString(tokenList)
    }

    private fun isFormatted(tokenList: List<Token>): Boolean {
        return OperatorIsFormatted().checkSemantic(tokenList)
    }

    private fun hasZeroDivision(tokenList: List<Token>): Boolean {
        return HasZeroDivision().checkSemantic(tokenList)
    }

    private fun hasNoString(tokenList: List<Token>): Boolean {
        for (token in tokenList) {
            when (token.type) {
                TokenType.STRING_LITERAL -> throw InvalidDataTypeException(
                    "Invalid data type: Expected Number Type data on." + "\n Failed on coord: (" +
                        tokenList.first().start.row + "; " + tokenList.first().start.column +
                        ")",
                )
                else -> continue
            }
        }
        return true
    }
}
