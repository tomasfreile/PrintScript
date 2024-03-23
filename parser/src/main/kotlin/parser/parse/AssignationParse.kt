package parser.parse

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import parser.sintactic.commons.HasCombination
import token.Token
import token.TokenType as Type

class AssignationParse : Parse {
    override fun parse(tokenList: List<Token>): Node {
        val token = tokenList.first()
        return when (token.type) {
            Type.SEMICOLON -> ASTSingleNode(null, token)
            Type.VALUE_IDENTIFIER -> {
                if (HasCombination().checkSyntax(tokenList)) {
                    ASTSingleNode(buildBinaryNode(tokenList.subList(1, tokenList.size)), token)
                } else {
                    ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token)
                }
            }
            else -> ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), token)
        }
    }

    private fun buildBinaryNode(tokenList: List<Token>): Node {
        val token = tokenList.first()
        return when (token.type) {
            Type.ASSIGNATION -> ASTSingleNode(buildBinaryNode(tokenList.subList(1, tokenList.size)), token)
            Type.STRING, Type.NUMBER, Type.VALUE_IDENTIFIER -> handleLiteral(tokenList)
            Type.SEMICOLON -> ASTSingleNode(null, token)
            else -> ASTSingleNode(null, token)
        }
    }

    private fun handleLiteral(tokenList: List<Token>): Node {
        val plusIndex = getPlusIndex(tokenList)
        return if (plusIndex != -1) {
            ASTBinaryNode(
                buildBinaryNode(tokenList.subList(plusIndex + 1, tokenList.size)),
                buildBinaryNode(tokenList.subList(0, plusIndex)),
                tokenList[plusIndex],
            )
        } else {
            if (tokenList.size == 1) {
                ASTSingleNode(null, tokenList.first())
            } else {
                ASTSingleNode(parse(tokenList.subList(1, tokenList.size)), tokenList.first())
            }
        }
    }

    private fun getPlusIndex(tokenList: List<Token>): Int {
        var index = 0
        for (token in tokenList) {
            when (token.type) {
                Type.PLUS -> return index
                else -> index += 1
            }
        }
        return -1
    }
}
