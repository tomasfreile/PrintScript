package parser.analysis.semantic

import parser.InvalidSyntaxException
import parser.analysis.sintactic.HasPairOfParen
import token.Token
import token.TokenType

class OperatorIsFormatted : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        return if (preCondition(tokenList)) {
            checkStructure(tokenList)
        } else {
            false
        }
    }

    private fun checkStructure(tokenList: List<Token>): Boolean {
        var index = 0
        var tokenCopy = tokenList
        while (index < tokenCopy.size && tokenCopy.isNotEmpty()) {
            val token = tokenCopy[index]
            index +=
                when {
                    isLiteral(token) -> 1
                    isLeftParen(token) -> {
                        tokenCopy = skipParenContent(tokenCopy)
                        index = 0
                        continue
                    }
                    isRightParen(token) -> 1
                    isOperator(token) -> if (isFormatted(tokenCopy, index)) 1 else return false
                    else -> return false
                }
        }
        return true
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL, TokenType.BOOLEAN_LITERAL -> true
            else -> false
        }
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.STAR, TokenType.SLASH, TokenType.MINUS -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun isRightParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }

    private fun skipParenContent(tokenList: List<Token>): List<Token> {
        return when {
            isParenContentValid(tokenList) -> tokenList.subList(getRightParenIndex(tokenList), tokenList.size)
            else -> throw InvalidSyntaxException(
                "Invalid Syntax Exception on line: " + tokenList.first().start.row + " | reason: Invalid Paren",
            )
        }
    }

    private fun isParenContentValid(tokenList: List<Token>): Boolean {
        return if (isParenValid(tokenList)) {
            val rightParenIndex = getRightParenIndex(tokenList)
            checkSemantic(tokenList.subList(1, rightParenIndex)) // goes and checks content
        } else {
            false
        }
    }

    private fun getRightParenIndex(tokenList: List<Token>): Int {
        var i = 0
        for (token in tokenList) {
            when (token.type) {
                TokenType.RIGHT_PAREN -> return i
                else -> i += 1
            }
        }
        return -1
    }

    private fun isParenValid(tokenList: List<Token>): Boolean {
        return HasPairOfParen().checkSyntax(tokenList)
    }

    private fun isFormatted(
        tokenList: List<Token>,
        index: Int,
    ): Boolean { // L O L ;)
        return isLiteral(tokenList[index - 1]) && isLiteral(tokenList[index + 1]) ||
            isRightParen(tokenList[index - 1]) && isLiteral(tokenList[index + 1]) ||
            isLiteral(tokenList[index - 1]) && isLeftParen(tokenList[index + 1])
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return !isOperator(tokenList[0]) && !isOperator(tokenList[tokenList.size - 1]) // No quiero que arranque ni termine con un operador
    }
}
