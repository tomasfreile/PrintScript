package sca

import ast.Node
import sca.rules.Rule

interface StaticCodeAnalyzer {
    fun analyze(ast: Node): List<String>
}

class StaticCodeAnalyzerImpl(private val rules : List<Rule>) : StaticCodeAnalyzer {

    override fun analyze(ast : Node): List<String> {
        val report = mutableListOf<String>()
        for (rule in rules) {
            val result = rule.validate(ast)
            when (result) {
                is StaticCodeAnalyzerResult.Error -> report.add(result.message)
                is StaticCodeAnalyzerResult.Ok -> continue
            }
        }
        return report
    }
}


