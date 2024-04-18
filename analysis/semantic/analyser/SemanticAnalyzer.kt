package parser.analysis.semantic.analyser

import parser.analysis.Result
import token.Token
import token.TokenType

/*
    tokenList is all, not just content. ContentType is String, Boolean, Number...
 */
interface SemanticAnalyzer {
    fun analyze(
        tokenList: List<Token>,
        contentType: TokenType,
    ): Result
}
