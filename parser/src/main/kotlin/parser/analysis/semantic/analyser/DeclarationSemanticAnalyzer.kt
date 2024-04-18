package parser.analysis.semantic.analyser

import parser.InvalidDataTypeException
import parser.analysis.Result
import parser.analysis.semantic.rule.SemanticRule
import token.Token
import token.TokenType

// Valid data literals for a type
class DeclarationSemanticAnalyzer(
    private val typeSemanticMap: Map<TokenType, List<SemanticRule>>,
    private val validDataTypeMap: Map<TokenType, List<TokenType>>,
) : SemanticAnalyzer {
    override fun analyze(
        tokenList: List<Token>,
        contentType: TokenType,
    ): Result {
        val valueType = tokenList[3].type
        val content = getContent(tokenList)
        if (content.isEmpty()) return Result.Ok // uninitialized variable
        return when {
            hasValidContent(contentType, valueType) -> checkContent(content, contentType)
            else -> throw InvalidDataTypeException(
                "Invalid content type " + contentType + " for value type " + valueType + " | on coords: (" +
                    tokenList.first().start.row.toString() + " ;" + tokenList.first().start.column.toString() + ")",
            )
        }
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

    private fun hasValidContent(
        contentType: TokenType,
        valueType: TokenType,
    ): Boolean {
        val validLiterals = validDataTypeMap[valueType] ?: throw NullPointerException("Invalid data type")
        return when {
            validLiterals.contains(contentType) -> true
            else -> false
        }
    }

    private fun getContent(tokenList: List<Token>): List<Token> {
        return if (tokenList.size == 5) emptyList() else tokenList.subList(5, tokenList.size - 1) // expression without semicolon
    }
}
