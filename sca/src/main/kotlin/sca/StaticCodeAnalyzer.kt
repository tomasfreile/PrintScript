package sca

import ast.AstNode

interface StaticCodeAnalyzer {
    fun analyze(ast: AstNode): List<String>
}

class StaticCodeAnalyzerImpl(fileName: String, version: String) : StaticCodeAnalyzer {
    private val rules = YamlReader(version).readRules(fileName)

    override fun analyze(ast: AstNode): List<String> {
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
