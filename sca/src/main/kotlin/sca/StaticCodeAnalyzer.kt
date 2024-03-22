package sca

import ast.Node
import sca.rules.readRules

interface StaticCodeAnalyzer {
    fun analyze(ast: Node): List<String>
}

class StaticCodeAnalyzerImpl(fileName: String) : StaticCodeAnalyzer {
    private val rules = readRules(fileName)

    override fun analyze(ast: Node): List<String> {
        val report = mutableListOf<String>()
        for (rule in rules) {
            when (val result = rule.validate(ast)) {
                is StaticCodeAnalyzerResult.Error -> report.add(result.message)
                is StaticCodeAnalyzerResult.Ok -> continue
            }
        }
        return report
    }
}
