package parser.analysis.syntax

import token.Token

interface SyntaxRule {
    fun checkSyntax(tokenList: List<Token>): Boolean
}
