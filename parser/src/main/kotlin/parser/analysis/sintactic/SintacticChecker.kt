package parser.analysis.sintactic

import token.Token

interface SintacticChecker {
    fun checkSyntax(tokenList: List<Token>): Boolean
}
