package parser.analysis.sintactic

import token.Token

interface SyntaxRule {
    fun checkSyntax(tokenList: List<Token>): Boolean
}
