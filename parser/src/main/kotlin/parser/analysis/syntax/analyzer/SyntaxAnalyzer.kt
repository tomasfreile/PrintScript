package parser.analysis.syntax.analyzer

import parser.analysis.Result
import token.Token
import token.TokenType

interface SyntaxAnalyzer {
    fun analyzeSyntax(tokenList: List<Token>): Result

    fun getSyntaxType(tokenList: List<Token>): TokenType
}
