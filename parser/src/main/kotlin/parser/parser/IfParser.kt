package parser.parser

import ast.AstNode
import parser.analysis.syntax.IsIfSyntax
import token.Token

class IfParser : Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        return preCondition(tokenList)
    }

    override fun createAST(tokenList: List<Token>): AstNode {
        TODO("Not yet implemented")
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return IsIfSyntax().checkSyntax(tokenList)
    }
}
