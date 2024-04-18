package parser.analysis.syntax.analyzer

import parser.InvalidSyntaxException
import parser.analysis.Result
import parser.analysis.syntax.rule.SyntaxRule
import token.Token
import token.TokenType

class SyntaxAnalyzerImpl(
    private val syntaxRulesMap: Map<TokenType, SyntaxRule>,
) : SyntaxAnalyzer {
    override fun analyzeSyntax(tokenList: List<Token>): Result {
        for (syntax in syntaxRulesMap.values) {
            when {
                syntax.checkSyntax(tokenList) -> return Result.Ok
                else -> continue
            }
        }
        return Result.NOTOK
    }

    override fun getSyntaxType(tokenList: List<Token>): TokenType {
        for ((type, syntax) in syntaxRulesMap) {
            when {
                syntax.checkSyntax(tokenList) -> return type
                else -> continue
            }
        }
        throw InvalidSyntaxException("Invalid syntax of expression on line: " + tokenList.first().start.row)
    }
}
