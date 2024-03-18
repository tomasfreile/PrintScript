package org.example.sca

import org.example.parser.Node
import org.example.sca.rules.Rule

interface StaticCodeAnalyzer {
    fun analyze(ast: Node): List<String>
}

class StaticCodeAnalyzerImpl(private val rules : List<Rule>) : StaticCodeAnalyzer {

    override fun analyze(ast : Node): List<String> {
        val report = mutableListOf<String>()
        for(rule in rules){
            val result = rule.validate(ast)
            report.add(result ?: continue)
        }
        return report
    }
}


