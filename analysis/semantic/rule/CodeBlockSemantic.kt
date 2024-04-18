package parser.analysis.semantic.rule

import parser.CodeBlockSeparator
import parser.analysis.syntax.rule.SyntaxRule
import token.Token
import token.TokenType

class CodeBlockSemantic(
    private val syntaxRules: Map<TokenType, SyntaxRule>,
    private val semanticRules: Map<TokenType, SemanticRule>,
    private val delimiterToken: TokenType,
) : SemanticRule {
    override fun checkSemantic(tokenList: List<Token>): Boolean {
        return when {
            preCondition(tokenList) -> checkExpressionsFromCodeBlock(tokenList)
            else -> false
        }
    }

    private fun preCondition(tokenList: List<Token>): Boolean {
        return tokenList.isNotEmpty()
    }

    private fun checkExpressionsFromCodeBlock(codeBlock: List<Token>): Boolean {
        val expressions = getExpressionsIndexes(codeBlock)
        for ((fromIndex, toIndex) in expressions) {
            for ((type, syntax) in syntaxRules) {
                val expression = codeBlock.subList(fromIndex, toIndex)
                when {
                    checkExpression(type, syntax, expression) -> break // At least there is one ok
                    else -> continue
                }
            }
        }
        return true
    }

    private fun checkExpression(
        type: TokenType,
        syntax: SyntaxRule,
        expression: List<Token>,
    ): Boolean {
        return when {
            syntax.checkSyntax(expression) -> {
                semanticRules[type]?.checkSemantic(expression) == true
            }
            else -> false
        }
    }

    private fun getExpressionsIndexes(content: List<Token>): List<Pair<Int, Int>> {
        return CodeBlockSeparator(delimiterToken).separateContentByIndexes(content)
    }
}
