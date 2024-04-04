package sca.rules

import ast.ASTSingleNode
import ast.Node
import sca.StaticCodeAnalyzerResult
import token.TokenType

class VariableNamingRule(private val namingConvention: NamingConvention) : Rule {
    override fun validate(ast: Node): StaticCodeAnalyzerResult {
        if (ast is ASTSingleNode && ast.token.type == TokenType.LET) {
            val regex = namingConvention.regex
            return validateVariableNames(ast, regex, namingConvention.display)
        }
        return StaticCodeAnalyzerResult.Ok
    }

    private fun validateVariableNames(
        node: ASTSingleNode,
        regex: String,
        displayName: String,
    ): StaticCodeAnalyzerResult {
        if (isVariableKeywordNode(node)) {
            val identifierNode = node.node
            if (identifierNode != null && !identifierNode.token.value.matches(Regex(regex))) {
                return StaticCodeAnalyzerResult.Error(
                    "Variable ${identifierNode.token.value} should be in $displayName. Position ${identifierNode.token.start.string()}",
                )
            }
        }
        return StaticCodeAnalyzerResult.Ok
    }

    private fun isVariableKeywordNode(node: ASTSingleNode) = node.node?.token?.type == TokenType.VALUE_IDENTIFIER_LITERAL
}

enum class NamingConvention(val regex: String, val display: String) {
    SNAKE_CASE("^[a-z][a-z0-9_]*$", "snake case"),
    CAMEL_CASE("^[a-z][a-zA-Z0-9]*$", "camel case"),
}
