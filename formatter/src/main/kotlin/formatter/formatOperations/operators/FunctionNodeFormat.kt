package formatter.formatOperations.operators

import ast.AstNode
import ast.FunctionNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation
import formatter.formatOperations.commons.FormatSemicolon
import token.TokenType

class FunctionNodeFormat : FormatOperation {
    private val semicolonHandler = FormatSemicolon()

    override fun canHandle(node: AstNode): Boolean {
        return node is FunctionNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as FunctionNode
        val function = defineFunctionSymbol(node.function)
        val expression = formatter.format(node.expression)
        return semicolonHandler.formatSemicolon("$function($expression)")
    }

    private fun defineFunctionSymbol(function: TokenType): String {
        return when (function) {
            TokenType.READINPUT -> "readInput"
            TokenType.READENV -> "readEnv"
            else -> ""
        }
    }
}
