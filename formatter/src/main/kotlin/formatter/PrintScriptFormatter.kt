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
            is BinaryOperationNode -> formatBinaryOperationNode(astNode, rulesReaderData)
            is PrintNode -> formatPrintNode(astNode, rulesReaderData)
            is VariableDeclarationNode -> formatVariableDeclarationNode(astNode, rulesReaderData)
            is AssignmentNode -> formatAssignmentNode(astNode, rulesReaderData)
//            is IfNode -> formatIfNode(astNode, rulesReaderData)
//            is FunctionNode -> formatFunctionNode(astNode, rulesReaderData)
            else -> ""
        }
    }

    private fun formatLiteralNode(node: LiteralNode): String {
        if (node.type == TokenType.STRING_LITERAL) {
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

//    private fun formatIfNode(
//        node: IfNode,
//        rules: Map<String, Any>,
//    ): String {
//        val ifJump = rules["ifIndentation"] as Int
//        val indentationSpaces = " ".repeat(ifJump)
//        val condition = removeBreakLines(applyFormat(node.condition, rules))
//        val thenBlock = removeBreakLines(applyFormat(node.thenBlock, rules))
//        val elseBlock = removeBreakLines(applyFormat(node.elseBlock, rules))
//        return "if ($condition) {\n$indentationSpaces$thenBlock\n} else {\n$indentationSpaces$elseBlock\n}"
//    }

//    private fun formatFunctionNode(
//        node: FunctionNode,
//        rules: Map<String, Any>,
//    ): String {
//        val function = defineFunctionSymbol(node.function)
//        val expression = applyFormat(node.expression, rules)
//        return "$function($expression)"
//    }

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
//            TokenType.CONST -> "const"
            else -> ""
        }
    }

    private fun defineValueType(valueType: TokenType): String {
        return when (valueType) {
            TokenType.NUMBER_TYPE -> "number"
            TokenType.STRING_TYPE -> "string"
            TokenType.BOOLEAN_TYPE -> "boolean"
            else -> ""
        }
    }

//    private fun defineFunctionSymbol(function: TokenType): String {
//        return when (function) {
//            TokenType.READ_INPUT -> "readInput"
//            TokenType.READ_ENV -> "readEnv"
//            TokenType.WRITE_ENV -> "writeEnv"
//            else -> ""
//        }
//    }

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

//    private fun removeBreakLines(result: String): String {
//        return removeInitialBreakLines(removeFinalBreakLines(result))
//    }
//
//    private fun removeInitialBreakLines(result: String): String {
//        if (result.startsWith("\n")) {
//            return removeInitialBreakLines(result.drop(1))
//        } else {
//            return result
//        }
//    }
//
//    private fun removeFinalBreakLines(result: String): String {
//        if (result.endsWith("\n")) {
//            return removeFinalBreakLines(result.dropLast(1))
//        } else {
//            return result
//        }
//    }
}
