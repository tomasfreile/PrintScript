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
            is IfNode -> formatIfNode(astNode, rulesReaderData)
            is FunctionNode -> formatFunctionNode(astNode, rulesReaderData)
            else -> ""
        }
    }

    private fun formatLiteralNode(node: LiteralNode): String {
        return node.value
    }

    private fun formatBinaryOperationNode(
        node: BinaryOperationNode,
        rules: Map<String, Any>,
    ): String {
        val left = applyFormat(node.left, rules)
        val right = applyFormat(node.right, rules)
        val operator = defineOperatorSymbol(node.operator)
        val equalsBefore = rules["assignationBefore"] as Boolean
        val equalsAfter = rules["assignationAfter"] as Boolean
        val formattedSpace = handleSpace(operator, equalsBefore, equalsAfter, left)
        return formattedSpace + right
    }

    private fun formatPrintNode(
        node: PrintNode,
        rules: Map<String, Any>,
    ): String {
        val printJump = rules["printJump"] as Int
        return "\n".repeat(printJump) + "println( " + applyFormat(node.expression, rules) + " )"
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
        return equals + applyFormat(node.expression, rules)
    }

    private fun formatAssignmentNode(
        node: AssignmentNode,
        rules: Map<String, Any>,
    ): String {
        val equalsBefore = rules["assignationBefore"] as Boolean
        val equalsAfter = rules["assignationAfter"] as Boolean
        return handleSpace("=", equalsBefore, equalsAfter, node.identifier) + applyFormat(node.expression, rules)
    }

    private fun formatIfNode(
        node: IfNode,
        rules: Map<String, Any>,
    ): String {
        val ifJump = rules["ifJump"] as Int
        val indentationSpaces = " ".repeat(ifJump)
        val condition = applyFormat(node.condition, rules)
        val thenBlock = applyFormat(node.thenBlock, rules)
        val elseBlock = applyFormat(node.elseBlock, rules)
        return "if ( $condition ) {\n$indentationSpaces$thenBlock\n} else {\n$indentationSpaces$elseBlock\n}"
    }

    private fun formatFunctionNode(
        node: FunctionNode,
        rules: Map<String, Any>,
    ): String {
        val function = defineFunctionSymbol(node.function)
        val expression = applyFormat(node.expression, rules)
        return "$function($expression)"
    }

//    private fun formatSemicolon(result: String): String {
//        return handleBeforeSpace(";", false, result) + "\n"
//    }

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
            TokenType.NUMBER_TYPE -> "number"
            TokenType.STRING_TYPE -> "string"
            TokenType.BOOLEAN_TYPE -> "boolean"
            else -> ""
        }
    }

    private fun defineFunctionSymbol(function: TokenType): String {
        return when (function) {
            TokenType.READ_INPUT -> "readInput"
            TokenType.READ_ENV -> "readEnv"
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

//    fun format(node: Node): String {
//        val ruleReaderData = RuleReader(rulesPath).readFile()
//        val tokenList = getTokenList(node)
//        return applyFormat(tokenList, ruleReaderData, "")
//    }
//
//    private fun getTokenList(node: Node?): List<Token> {
//        return when (node) {
//            is ASTSingleNode -> listOf(node.token) + getTokenList(node.node)
//            is ASTBinaryNode -> getTokenList(node.left) + listOf(node.token) + getTokenList(node.right)
//            else -> emptyList()
//        }
//    }
//
//    private fun applyFormat(
//        tokenList: List<Token>,
//        rulesData: Map<String, Any>,
//        result: String,
//    ): String {
//        return when {
//            tokenList.isEmpty() -> result
//            else -> {
//                applyRules(tokenList, rulesData, result)
//            }
//        }
//    }
//
//    private fun applyRules(
//        tokenList: List<Token>,
//        rulesData: Map<String, Any>,
//        result: String,
//    ): String {
//        val token = tokenList.first()
//        val formattedToken = formatToken(token, rulesData, result)
//        return applyFormat(tokenList.drop(1), rulesData, formattedToken)
//    }
//
//    private fun formatToken(
//        token: Token,
//        rulesData: Map<String, Any>,
//        result: String,
//    ): String {
//        val tokenValue = token.value
//        return when (token.type) {
//            TokenType.COLON -> formatColon(tokenValue, rulesData, result)
//            TokenType.ASSIGNATION -> formatAssignation(tokenValue, rulesData, result)
//            TokenType.PRINT -> formatPrintln(tokenValue, rulesData, result)
//            TokenType.PLUS -> formatOperator(tokenValue, result)
//            TokenType.MINUS -> formatOperator(tokenValue, result)
//            TokenType.STAR -> formatOperator(tokenValue, result)
//            TokenType.SEMICOLON -> formatSemicolon(tokenValue, result)
//            else -> formatRegularToken(tokenValue, result)
//        }
//    }
//
//    private fun formatColon(
//        tokenValue: String,
//        rulesData: Map<String, Any>,
//        result: String,
//    ): String {
//        val colonBefore = rulesData["colonBefore"] as Boolean
//        val colonAfter = rulesData["colonAfter"] as Boolean
//        val formattedBefore = handleBeforeSpace(tokenValue, colonBefore, result)
//        return handleAfterSpace(formattedBefore, colonAfter)
//    }
//
//    private fun formatAssignation(
//        tokenValue: String,
//        rulesData: Map<String, Any>,
//        result: String,
//    ): String {
//        val equalsBefore = rulesData["assignationBefore"] as Boolean
//        val equalsAfter = rulesData["assignationAfter"] as Boolean
//        val formattedBefore = handleBeforeSpace(tokenValue, equalsBefore, result)
//        return handleAfterSpace(formattedBefore, equalsAfter)
//    }
//
//    private fun formatPrintln(
//        tokenValue: String,
//        rulesData: Map<String, Any>,
//        result: String,
//    ): String {
//        val printJump = rulesData["printJump"] as Int
//        if (result.endsWith(" ")) {
//            val modifiedResult = result.dropLast(1)
//            return modifiedResult + "\n".repeat(printJump) + tokenValue
//        } else {
//            return result + "\n".repeat(printJump) + tokenValue
//        }
//    }
//
//    private fun formatOperator(
//        tokenValue: String,
//        result: String,
//    ): String {
//        val formattedBefore = handleBeforeSpace(tokenValue, true, result)
//        return handleAfterSpace(formattedBefore, true)
//    }
//

//
//    private fun formatRegularToken(
//        tokenValue: String,
//        result: String,
//    ): String {
//        return "$result$tokenValue "
//    }
//
}
