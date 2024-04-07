package parser.analysis.semantic

import token.Token

interface SemanticRule {
    fun checkSemantic(tokenList: List<Token>): Boolean
}
