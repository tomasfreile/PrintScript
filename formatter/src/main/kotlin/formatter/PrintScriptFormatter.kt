@file:Suppress("ktlint:standard:no-wildcard-imports")

package formatter

import ast.*
import formatter.formatOperations.FormatOperation
import ruleReader.RuleReader

class PrintScriptFormatter(rulesPath: String, private val formattingOperators: List<FormatOperation>) : Formatter {
    private val rulesReaderData = RuleReader(rulesPath).readFile()

    // revisa la lista de operadores de formato y pregunta si puede manejar el nodo, si puede lo formatea
    override fun format(astNode: AstNode): String {
        for (operator in formattingOperators) {
            if (operator.canHandle(astNode)) {
                return operator.format(astNode, this)
            }
        }
        throw UnsupportedOperationException("Unsupported node type: ${astNode.javaClass}")
    }

    // consigue el mapa de reglas
    override fun getRules(): Map<String, Any> {
        return rulesReaderData
    }
}
