package parser.analysis.semantic.rule

import token.Token

interface SemanticRule {
    fun checkSemantic(tokenList: List<Token>): Boolean
}
