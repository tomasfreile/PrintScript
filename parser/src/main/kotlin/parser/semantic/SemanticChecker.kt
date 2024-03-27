package parser.semantic

import token.Token

interface SemanticChecker {
    fun checkSemantic(tokenList: List<Token>): Boolean
}
