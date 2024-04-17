@file:Suppress("ktlint:standard:no-wildcard-imports")

package formatter

import ast.*
import ruleReader.RuleReader
import token.TokenType

class PrintScriptFormatter(private val rulesPath: String) : Formatter {
    override fun format(astNode: AstNode): String {
        val rulesReaderData = RuleReader(rulesPath).readFile()
        return applyFormat(astNode, rulesReaderData)
    }

    private fun applyFormat(
        astNode: AstNode,
        rulesReaderData: Map<String, Any>,
    ): String {
        return when (astNode) {
            is LiteralNode -> formatLiteralNode(astNode)
            is CodeBlock -> formatCodeBlock(astNode, rulesReaderData)
            is BinaryOperationNode -> formatBinaryOperationNode(astNode, rulesReaderData)
            is PrintNode -> formatPrintNode(astNode, rulesReaderData)
            is VariableDeclarationNode -> formatVariableDeclarationNode(astNode, rulesReaderData)
            is AssignmentNode -> formatAssignmentNode(astNode, rulesReaderData)
            is IfNode -> formatIfNode(astNode, rulesReaderData)
            is FunctionNode -> formatFunctionNode(astNode, rulesReaderData)
            is NilNode -> ""
            else -> throw UnsupportedOperationException("Unsupported node type: ${astNode.javaClass}")
        }
    }

    private fun formatLiteralNode(node: LiteralNode): String {
        if (node.type == TokenType.STRINGLITERAL) {
            return "\"${node.value}\""
        }
        return node.value
    }

    private fun formatBinaryOperationNode(
        node: BinaryOperationNode,
        rules: Map<String, Any>,
    ): String {
        val left = applyFormat(node.left, rules)
        val right = applyFormat(node.right, rules)
        val operator = defineOperatorSymbol(node.operator)
        return if (node.left is BinaryOperationNode && node.right is BinaryOperationNode) {
            "($left) $operator ($right)"
        } else if (node.left is BinaryOperationNode) {
            "($left) $operator $right"
        } else if (node.right is BinaryOperationNode) {
            "$left $operator ($right)"
        } else {
            "$left $operator $right"
        }
    }

    private fun formatPrintNode(
        node: PrintNode,
        rules: Map<String, Any>,
    ): String {
        val printJump = rules["printJump"] as Int
        val format = "\n".repeat(printJump) + "println(" + applyFormat(node.expression, rules) + ")"
        return formatSemicolon(format)
    }

    private fun formatVariableDeclarationNode(
        node: VariableDeclarationNode,
        rules: Map<String, Any>,
    ): String {
        val colonBefore = rules["colonBefore"] as Boolean
        val colonAfter = rules["colonAfter"] as Boolean
        val equalsBefore = rules["assignationBefore"] as Boolean
        val equalsAfter = rules["assignationAfter"] as Boolean
        val variableSymbol = defineVariableSymbol(node.declarationType)
        val identifier = variableSymbol + " " + node.identifier
        val colon = handleSpace(":", colonBefore, colonAfter, identifier)
        val valueTypeSymbol = defineValueType(node.valueType)
        val valueType = colon + valueTypeSymbol
        val equals = handleSpace("=", equalsBefore, equalsAfter, valueType)
        val format = equals + applyFormat(node.expression, rules)
        return formatSemicolon(format)
    }

    private fun formatAssignmentNode(
        node: AssignmentNode,
        rules: Map<String, Any>,
    ): String {
        val equalsBefore = rules["assignationBefore"] as Boolean
        val equalsAfter = rules["assignationAfter"] as Boolean
        val format = handleSpace("=", equalsBefore, equalsAfter, node.identifier) + applyFormat(node.expression, rules)
        return formatSemicolon(format)
    }

    private fun formatIfNode(
        node: IfNode,
        rules: Map<String, Any>,
    ): String {
        val indentationAmount = rules["ifIndentation"] as Int
        val condition = applyFormat(node.condition, rules)
        val thenBlock = applyIndentations(applyFormat(node.thenBlock, rules), indentationAmount)
        val elseBlock = applyIndentations(applyFormat(node.elseBlock, rules), indentationAmount)
        return "if ($condition) {\n$thenBlock\n} else {\n$elseBlock\n}\n"
    }

    private fun formatCodeBlock(
        node: CodeBlock,
        rules: Map<String, Any>,
    ): String {
        return formatCodeBlockLoop(node, rules, "")
    }

    private fun formatFunctionNode(
        node: FunctionNode,
        rules: Map<String, Any>,
    ): String {
        val function = defineFunctionSymbol(node.function)
        val expression = applyFormat(node.expression, rules)
        return formatSemicolon("$function($expression)")
    }

    private fun formatCodeBlockLoop(
        node: CodeBlock,
        rules: Map<String, Any>,
        result: String,
    ): String {
        return if (node.nodes.isEmpty()) {
            result
        } else {
            val newResult = result + applyFormat(node.nodes[0], rules)
            formatCodeBlockLoop(CodeBlock(node.nodes.drop(1), node.position), rules, newResult)
        }
    }

    private fun formatSemicolon(result: String): String {
        return handleBeforeSpace(";", false, result) + "\n"
    }

    private fun defineOperatorSymbol(operator: TokenType): String {
        return when (operator) {
            TokenType.PLUS -> "+"
            TokenType.MINUS -> "-"
            TokenType.STAR -> "*"
            TokenType.SLASH -> "/"
            else -> ""
        }
    }

    private fun defineVariableSymbol(declarationType: TokenType): String {
        return when (declarationType) {
            TokenType.LET -> "let"
            TokenType.CONST -> "const"
            else -> ""
        }
    }

    private fun defineValueType(valueType: TokenType): String {
        return when (valueType) {
            TokenType.NUMBERTYPE -> "number"
            TokenType.STRINGTYPE -> "string"
            TokenType.BOOLEANTYPE -> "boolean"
            else -> ""
        }
    }

    private fun handleSpace(
        tokenValue: String,
        spaceBefore: Boolean,
        spaceAfter: Boolean,
        result: String,
    ): String {
        val formattedBefore = handleBeforeSpace(tokenValue, spaceBefore, result)
        return handleAfterSpace(formattedBefore, spaceAfter)
    }

    private fun handleBeforeSpace(
        tokenValue: String,
        spaceBefore: Boolean,
        result: String,
    ): String {
        return if (spaceBefore && result.endsWith(" ")) {
            result + tokenValue
        } else if (spaceBefore && !result.endsWith(" ")) {
            "$result $tokenValue"
        } else if (!spaceBefore && result.endsWith(" ")) {
            val modifiedResult = result.dropLast(1)
            modifiedResult + tokenValue
        } else {
            result + tokenValue
        }
    }

    private fun handleAfterSpace(
        result: String,
        spaceAfter: Boolean,
    ): String {
        return if (spaceAfter) {
            "$result "
        } else {
            result
        }
    }

    private fun applyIndentations(
        result: String,
        indentations: Int,
    ): String {
        val indentationSpaces = "\t".repeat(indentations)
        val dividedResult = result.split("\n")

        return applyIndentationsLoop(dividedResult, indentationSpaces, "")
    }

    private fun applyIndentationsLoop(
        dividedResult: List<String>,
        indentations: String,
        result: String,
    ): String {
        if (dividedResult.isEmpty()) {
            return result
        } else if (dividedResult.size == 1) {
            return result + indentations + dividedResult[0]
        } else {
            val newResult = result + indentations + dividedResult[0] + "\n"
            return applyIndentationsLoop(dividedResult.drop(1), indentations, newResult)
        }
    }

    private fun defineFunctionSymbol(function: TokenType): String {
        return when (function) {
            TokenType.READINPUT -> "readInput"
            TokenType.READENV -> "readEnv"
            else -> ""
        }
    }
}
