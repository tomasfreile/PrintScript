package sca.rules

import ast.ASTSingleNode
import ast.Node
import token.TokenType

//class VariableNamesShouldBeCamelCase : Rule {
//    override fun validate(ast: Node): String? {
//        if (ast is ASTSingleNode && ast.token.type == TokenType.VARIABLE_KEYWORD) {
//            return validateVariableNames(ast, "^[a-z][a-zA-Z0-9]*$")
//        }
//        return null
//    }

//    private fun validateVariableNames(node: Node?, regex: String): String? {
//        if (node is ASTSingleNode && node.node?.token?.type == TokenType.VALUE_IDENTIFIER) {
//            val variableName = node.node.token.value
//            if (!variableName.matches(Regex(regex))) {
//                return "Variable name '$variableName' should be in camelCase"
//            }
//        }
//        return null
//    }
//}
