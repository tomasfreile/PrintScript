package parser.analysis.semantic.common

import parser.InvalidOperatorException
import parser.InvalidParenException
import parser.analysis.semantic.SemanticRule
import parser.analysis.syntax.common.HasPairSeparator
import token.Token
import token.TokenType

class OperatorIsFormatted : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        return if (preCondition(tokenList)) {
            checkStructure(tokenList)
        } else {
            throw InvalidOperatorException(
                "Invalid precondition, expression starts or ends with operators on line: " + tokenList.first().start.row,
            )
        }
    }

    private fun checkStructure(tokenList: List<Token>): Boolean {
        var index = 0
        var tokenCopy = tokenList
        while (condition(tokenCopy, index)) {
            val token = tokenCopy[index]
            when {
                isLiteral(token) -> index += 1
                isLeftParen(token) -> {
                    tokenCopy = skipParenContent(tokenCopy)
                    index = 0
                    continue
                }
                isRightParen(token) -> index += 1
                isOperator(token) -> if (isFormatted(tokenCopy, index)) index += 1 else throwException(token)
                else -> throwException(token)
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
            !isLeftParen(tokenList.first()) -> skipParenContent(tokenList.subList(1, tokenList.size))
            isParenContentValid(tokenList) -> tokenList.subList(getRightParenIndex(tokenList) + 1, tokenList.size)
            else -> throw InvalidParenException(
                "Invalid Syntax Exception on coord: (" +
                    tokenList.first().start.row + "; " + tokenList.first().start.column +
                    ") | reason: Invalid Paren",
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
        return HasPairSeparator(
            TokenType.LEFT_PAREN,
            TokenType.RIGHT_PAREN,
        ).checkSyntax(tokenList)
    }

    private fun isFormatted(
        tokenList: List<Token>,
        index: Int,
    ): Boolean { // L O L ;)
        return if (index == 0 && tokenList.size > 2) {
            isOperator(tokenList[index]) && isLeftParen(tokenList[1])
        } else if (tokenList.size == 2) {
            isOperator(tokenList[index]) && isLiteral(tokenList[index + 1])
        } else {
            isLiteral(tokenList[index - 1]) && isLiteral(tokenList[index + 1]) ||
                isRightParen(tokenList[index - 1]) && isLiteral(tokenList[index + 1]) ||
                isLiteral(tokenList[index - 1]) && isLeftParen(tokenList[index + 1])
        }
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return !isOperator(tokenList[0]) && !isOperator(tokenList[tokenList.size - 1]) // No quiero que arranque ni termine con un operador
    }

    private fun condition(
        tokenList: List<Token>,
        index: Int,
    ): Boolean {
        return index < tokenList.size && tokenList.isNotEmpty()
    }

    private fun throwException(token: Token) {
        throw InvalidOperatorException(message = "Invalid Operator on coord: (" + token.start.row + "; " + token.start.column + ")")
    }
}
