package parser

import ast.AstNode
import parser.parser.Parser
import token.Token

class PrintScriptParser(private val parserList: List<Parser>) :
    Parser {
    override fun canHandle(tokenList: List<Token>): Boolean {
        for (parser in parserList) {
            when {
                parser.canHandle(tokenList) -> return true
                else -> continue
            }
        }
        return false
    }

    override fun createAST(tokenList: List<Token>): AstNode? {
        for (parser in parserList) {
            when {
                parser.canHandle(tokenList) -> return parser.createAST(tokenList)
                else -> continue
            }
        }
        throw InvalidSyntaxException("Invalid syntax on line ${tokenList.first().start.row}")
    }
}
