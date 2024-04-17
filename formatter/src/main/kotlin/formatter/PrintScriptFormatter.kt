@file:Suppress("ktlint:standard:no-wildcard-imports")

package formatter

import ast.*
import formatter.formatOperations.FormatOperation
import ruleReader.RuleReader

class PrintScriptFormatter(rulesPath: String, private val formattingOperators: List<FormatOperation>) : Formatter {
    private val rulesReaderData = RuleReader(rulesPath).readFile()

    override fun format(astNode: AstNode): String {
        for (operator in formattingOperators) {
            if (operator.canHandle(astNode)) {
                return operator.format(astNode, this)
            }
        }
        throw UnsupportedOperationException("Unsupported node type: ${astNode.javaClass}")
    }

    override fun getRules(): Map<String, Any> {
        return rulesReaderData
    }
}
