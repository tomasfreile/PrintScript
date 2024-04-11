package parser.analysis.syntax.rule

import token.Token

interface SyntaxRule {
    fun checkSyntax(tokenList: List<Token>): Boolean
}
