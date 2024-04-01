package parser.parse

import ast.ASTSingleNode
import ast.Node
import parser.utils.BinaryNodeBuilder
import token.Token
import token.TokenType

class PrintParse : Parse {
    override fun parse(tokenList: List<Token>): Node {
        val token = tokenList.first()
        return when {
            breakRecursion(token) -> ASTSingleNode(null, token)
            isPrint(token) -> ASTSingleNode(parse(tokenList.subList(2, tokenList.size)), token) // ignore first left paren
            isLiteral(token) || isLeftParen(token) -> handleLiteral(tokenList)
            ignore(token) -> parse(tokenList.subList(1, tokenList.size))
            else -> ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token)
        }
    }

    private fun handleLiteral(tokenList: List<Token>): Node { // if next is an operator
        return if (tokenList.size == 1) {
            ASTSingleNode(null, tokenList.first())
        } else if (isBinaryNode(tokenList)) {
            BinaryNodeBuilder(PrintParse()).build(tokenList)
        } else {
            ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), tokenList.first())
        }
    }

    private fun isLiteral(token: Token): Boolean {
        return when (token.type) {
            TokenType.STRING, TokenType.NUMBER, TokenType.VALUE_IDENTIFIER -> true
            else -> false
        }
    }

    private fun isOperator(token: Token): Boolean {
        return when (token.type) {
            TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH -> true
            else -> false
        }
    }

    private fun isBinaryNode(tokenList: List<Token>): Boolean {
        return isLiteral(tokenList.first()) && isOperator(tokenList[1]) ||
            isLeftParen(tokenList.first()) && isLiteral(tokenList[1])
    }

    private fun breakRecursion(token: Token): Boolean {
        return when (token.type) {
            TokenType.SEMICOLON -> true
            else -> false
        }
    }

    private fun ignore(token: Token): Boolean {
        return when (token.type) {
            TokenType.RIGHT_PAREN, TokenType.LEFT_PAREN -> true
            else -> false
        }
    }

    private fun isPrint(token: Token): Boolean {
        return when (token.type) {
            TokenType.PRINT -> true
            else -> false
        }
    }

    private fun isLeftParen(token: Token): Boolean {
        return when (token.type) {
            TokenType.LEFT_PAREN -> true
            else -> false
        }
    }
}
