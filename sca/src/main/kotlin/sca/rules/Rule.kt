package sca.rules

import ast.Node
import sca.StaticCodeAnalyzerResult

interface Rule {
    fun validate(ast: Node): StaticCodeAnalyzerResult
}
