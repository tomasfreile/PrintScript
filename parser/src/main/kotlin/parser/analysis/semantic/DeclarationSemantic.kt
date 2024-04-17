package parser.analysis.semantic

import ast.AstNode
import ast.VariableDeclarationNode
import token.TokenType

class DeclarationSemantic(
    private val typeValueMap: Map<TokenType, TokenType>,
) : SemanticAnalyser {
    override fun analyseAst(node: AstNode): Boolean {
        node as VariableDeclarationNode
        val content = node.expression
        val type = node.declarationType
        return true
    }
}
