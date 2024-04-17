package parser.analysis.semantic

import ast.AstNode

interface SemanticAnalyser {
    fun analyseAst(node: AstNode): Boolean
}
