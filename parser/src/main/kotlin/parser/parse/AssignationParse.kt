package parser.parse

import ast.ASTSingleNode
import ast.Node
import parser.utils.BinaryNodeBuilder
import token.Token
import token.TokenType

class AssignationParse : Parse {
    override fun parse(tokenList: List<Token>): Node {
        val token = tokenList.first()
        return when {
            breakRecursion(token) -> ASTSingleNode(null, token)
            isLiteral(token) || isLeftParen(token) -> handleBuild(tokenList)
            ignore(token) -> parse(tokenList.subList(1, tokenList.size))
            else -> ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token)
        }
    }

    private fun handleBuild(tokenList: List<Token>): Node { // if next is an operator
        return if (tokenList.size == 1) {
            ASTSingleNode(null, tokenList.first())
        } else if (isBinaryNode(tokenList)) {
            BinaryNodeBuilder(this).build(tokenList)
        } else {
            ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), tokenList.first())
        }
    }

    private fun isBinaryNode(tokenList: List<Token>): Boolean {
        return isLiteral(tokenList.first()) && isOperator(tokenList[1]) ||
            isLeftParen(tokenList.first()) && isLiteral(tokenList[1])
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING_LITERAL, TokenType.NUMBER_LITERAL, TokenType.VALUE_IDENTIFIER_LITERAL -> true
            else -> false
        }
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun breakRecursion(token: Token): Boolean {
        return when (token.type) {
            TokenType.SEMICOLON -> true
            else -> false
        }
    }

    private fun ignore(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN, TokenType.RIGHT_PAREN -> true
            else -> false
        }
    }
}
