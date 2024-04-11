package sca.rules

import ast.AstNode
import sca.StaticCodeAnalyzerResult

interface Rule {
    fun validate(node: AstNode): StaticCodeAnalyzerResult
}
