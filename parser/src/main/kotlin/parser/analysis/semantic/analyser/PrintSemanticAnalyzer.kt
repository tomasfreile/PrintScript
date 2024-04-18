package parser.analysis.semantic.analyser

import parser.analysis.Result
import parser.analysis.semantic.rule.SemanticRule
import token.Token
import token.TokenType

class PrintSemanticAnalyzer(
    private val typeSemanticMap: Map<TokenType, List<SemanticRule>>,
) : SemanticAnalyzer {
    override fun analyze(
        tokenList: List<Token>,
        contentType: TokenType,
    ): Result {
        val content = getContent(tokenList)
        return checkContent(content, contentType)
    }

    private fun checkContent(
        content: List<Token>,
        contentType: TokenType,
    ): Result {
        val semantics = typeSemanticMap[contentType] ?: return Result.NOTOK
        for (semantic in semantics) {
            when {
                semantic.checkSemantic(content) -> return Result.Ok
                else -> continue
            }
        }
        return Result.NOTOK
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size - 2)
    }
}
