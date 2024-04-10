@file:Suppress("ktlint:standard:no-wildcard-imports")

package formatter

import ast.*

class PrintScriptFormatter(private val rulesPath: String) : Formatter {
    override fun format(astNode: AstNode): String {
//        val rulesReaderData = RuleReader(rulesPath).readFile()
//        return applyFormat(astNode, rulesReaderData, "")
        return "hello"
    }
//
//    private fun applyFormat(
//        astNode: AstNode,
//        rulesReaderData: Map<String, Any>,
//        result: String,
//    ): String {
//        return when (astNode) {
//            is LiteralNode -> formatLiteralNode(astNode, rulesReaderData, result)
//            is BinaryOperationNode -> formatBinaryOperationNode(astNode, rulesReaderData, result)
//            is PrintNode -> formatPrintNode(astNode, rulesReaderData, result)
//            is VariableDeclarationNode -> formatVariableDeclarationNode(astNode, rulesReaderData, result)
//            is AssignmentNode -> formatAssignmentNode(astNode, rulesReaderData, result)
//            is IfNode -> formatIfNode(astNode, rulesReaderData, result)
//            is FunctionNode -> formatFunctionNode(astNode, rulesReaderData, result)
//            else -> result
//        }
//    }
//
//    private fun formatLiteralNode(
//        node: LiteralNode,
//        rules: Map<String, Any>,
//        result: String,
//    ): String {
//        return node.value
//    }
//
//    private fun formatBinaryOperationNode(
//        node: BinaryOperationNode,
//        rules: Map<String, Any>,
//        result: String,
//    ): String {
//        val left = applyFormat(node.left, rules, "")
//        val right = applyFormat(node.right, rules, "")
//        val operator = defineOperatorSymbol(node.operator)
//        val equalsBefore = rules["assignationBefore"] as Boolean
//        val equalsAfter = rules["assignationAfter"] as Boolean
//        val formattedBeforeSpace = handleBeforeSpace(operator, equalsBefore, left)
//        val formattedAfterSpace = handleAfterSpace(formattedBeforeSpace, equalsAfter)
//        return formattedAfterSpace + right
//    }
//
//    private fun formatPrintNode(
//        node: PrintNode,
//        rules: Map<String, Any>,
//        result: String,
//    ): String {
//        val printJump = rules["printJump"] as Int
//        return "\n".repeat(printJump) + "println( " + applyFormat(node.expression, rules, "") + " )"
//    }
//
//    private fun formatVariableDeclarationNode() {
//    }
//
//    private fun defineOperatorSymbol(operator: TokenType): String {
//        return when (operator) {
//            TokenType.PLUS -> "+"
//            TokenType.MINUS -> "-"
//            TokenType.STAR -> "*"
//            TokenType.SLASH -> "/"
//            else -> ""
//        }
//    }
//
//    private fun handleBeforeSpace(
//        tokenValue: String,
//        spaceBefore: Boolean,
//        result: String,
//    ): String {
//        return if (spaceBefore && result.endsWith(" ")) {
//            result + tokenValue
//        } else if (spaceBefore && !result.endsWith(" ")) {
//            "$result $tokenValue"
//        } else if (!spaceBefore && result.endsWith(" ")) {
//            val modifiedResult = result.dropLast(1)
//            modifiedResult + tokenValue
//        } else {
//            result + tokenValue
//        }
//    }
//
//    private fun handleAfterSpace(
//        result: String,
//        spaceAfter: Boolean,
//    ): String {
//        return if (spaceAfter) {
//            "$result "
//        } else {
//            result
//        }
//    }

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
//    private fun formatSemicolon(
//        tokenValue: String,
//        result: String,
//    ): String {
//        return handleBeforeSpace(tokenValue, false, result) + "\n"
//    }
//
//    private fun formatRegularToken(
//        tokenValue: String,
//        result: String,
//    ): String {
//        return "$result$tokenValue "
//    }
//
}
