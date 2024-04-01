package parser.analysis.semantic

import token.Token

interface SemanticChecker {
    fun checkSemantic(tokenList: List<Token>): Boolean
}
