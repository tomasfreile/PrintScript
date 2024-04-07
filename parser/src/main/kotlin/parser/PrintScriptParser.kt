package parser

import ast.AstNode
import parser.parser.Parser
import token.Token

class PrintScriptParser(private val parserList: List<Parser>) {
    fun parse(tokenList: List<Token>): AstNode {
        for (parser in parserList) {
            when {
                parser.canHandle(tokenList) -> return parser.createAST(tokenList)
                else -> continue
            }
        }
        throw InvalidSyntaxException("Invalid syntax")
    }
}
