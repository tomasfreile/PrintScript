package parser.sintactic.commons

import parser.Type
import parser.sintactic.IsAssignation
import parser.sintactic.IsDeclarative
import parser.sintactic.IsPrint
import parser.sintactic.SintacticChecker
import token.Token
import token.TokenType

class HasValidParen : SintacticChecker {
    override fun checkSyntax(tokenList: List<Token>): Boolean {
        val type = getParseType(tokenList)
        return when (type) {
            Type.ASSIGNATION -> checkStructure(tokenList.subList(2, tokenList.size - 1)) // THIS IS THE CONTENT
            Type.PRINT -> checkStructure(tokenList.subList(2, tokenList.size - 2))
            Type.DECLARATION -> checkStructure(tokenList.subList(5, tokenList.size - 1))
            Type.NIL -> false
        }
    }

    private fun checkStructure(tokenList: List<Token>): Boolean {
        var tokenCopy = tokenList
        var index = 0
        while (tokenCopy.isNotEmpty()) {
            when (tokenCopy[index].type) {
                TokenType.LEFT_PAREN -> { // if there is a left paren, there has to be a right paren on list
                    val rightParenIndex = getRightParen(tokenCopy)
                    if (rightParenIndex > 0) { // if right paren exists, continue please
                        tokenCopy = tokenCopy.subList(rightParenIndex + 1, tokenCopy.size)
                        index = 0
                    } else {
                        return false // if right paren does not exist, false. die
                    }
                }
                TokenType.RIGHT_PAREN -> return false // There will never be a right paren if left paren does not exist
                TokenType.SEMICOLON -> break
                else -> index += 1
            }
        }
        return true // : )
    }

    private fun getRightParen(tokenList: List<Token>): Int {
        var index = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.RIGHT_PAREN -> return index
                else -> index += 1
            }
        }
        return -1
    }

    private fun getParseType(tokenList: List<Token>): Type {
        return when {
            IsDeclarative().checkSyntax(tokenList) -> Type.DECLARATION
            IsPrint().checkSyntax(tokenList) -> Type.PRINT
            IsAssignation().checkSyntax(tokenList) -> Type.ASSIGNATION
            else -> Type.NIL
        }
    }
}
