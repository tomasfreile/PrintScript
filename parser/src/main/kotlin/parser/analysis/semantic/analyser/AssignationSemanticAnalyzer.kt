package parser.analysis.semantic.analyser

import parser.analysis.Result
import parser.analysis.semantic.rule.SemanticRule
import token.Token
import token.TokenType

class AssignationSemanticAnalyzer(
    private val typeSemanticMap: Map<TokenType, List<SemanticRule>>,
) : SemanticAnalyzer {
    override fun analyze(
        tokenList: List<Token>,
        contentType: TokenType,
    ): Result {
        val content = getContent(tokenList)
        return getResult(content, contentType)
    }

    private fun getResult(
        content: List<Token>,
        valueType: TokenType,
    ): Result {
        val semantics = typeSemanticMap[valueType] ?: return Result.NOTOK
        for (semantic in semantics) {
            when {
                semantic.checkSemantic(content) -> continue
                else -> return Result.NOTOK
            }
        }
        return Result.Ok
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return tokenList.subList(2, tokenList.size - 1)
    }
}
