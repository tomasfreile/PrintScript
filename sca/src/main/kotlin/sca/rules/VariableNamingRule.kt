package sca.rules

import ast.AstNode
import ast.VariableDeclarationNode
import sca.StaticCodeAnalyzerResult

class VariableNamingRule(private val namingConvention: NamingConvention) : Rule {
    override fun validate(node: AstNode): StaticCodeAnalyzerResult {
        return when (node) {
            is VariableDeclarationNode -> {
                if (namingConvention.regex.toRegex().matches(node.identifier)) {
                    StaticCodeAnalyzerResult.Ok
                } else {
                    StaticCodeAnalyzerResult.Error("Variable name ${node.identifier} does not match ${namingConvention.display}")
                }
            }

            else -> StaticCodeAnalyzerResult.Ok
        }
    }
}

enum class NamingConvention(val regex: String, val display: String) {
    SNAKE_CASE("^[a-z][a-z0-9_]*$", "snake case"),
    CAMEL_CASE("^[a-z][a-zA-Z0-9]*$", "camel case"),
}
