package formatter

import ast.ASTBinaryNode
import ast.ASTSingleNode
import ast.Node
import ruleReader.RuleReader
import token.Token
import token.TokenType

class Formatter(private val rulesPath: String) {
    fun format(node: Node): String {
        val ruleReaderData = RuleReader(rulesPath).readFile()
        val tokenList = getTokenList(node)
        return applyFormat(tokenList, ruleReaderData, "")
    }

    private fun getTokenList(node: Node?): List<Token> {
        return when (node) {
            is ASTSingleNode -> listOf(node.token) + getTokenList(node.node)
            is ASTBinaryNode -> getTokenList(node.left) + listOf(node.token) + getTokenList(node.right)
            else -> emptyList()
        }
    }

    private fun applyFormat(
        tokenList: List<Token>,
        rulesData: Map<String, Any>,
        result: String,
    ): String {
        return when {
            tokenList.isEmpty() -> result
            else -> {
                applyRules(tokenList, rulesData, result)
            }
        }
    }

    private fun applyRules(
        tokenList: List<Token>,
        rulesData: Map<String, Any>,
        result: String,
    ): String {
        val token = tokenList.first()
        val formattedToken = formatToken(token, rulesData, result)
        return applyFormat(tokenList.drop(1), rulesData, formattedToken)
    }

    private fun formatToken(
        token: Token,
        rulesData: Map<String, Any>,
        result: String,
    ): String {
        val tokenValue = token.value
        return when (token.type) {
            TokenType.COLON -> formatColon(tokenValue, rulesData, result)
            TokenType.ASSIGNATION -> formatAssignation(tokenValue, rulesData, result)
            TokenType.PRINT -> formatPrintln(tokenValue, rulesData, result)
            TokenType.PLUS -> formatOperator(tokenValue, result)
            TokenType.MINUS -> formatOperator(tokenValue, result)
            TokenType.STAR -> formatOperator(tokenValue, result)
            TokenType.SEMICOLON -> formatSemicolon(tokenValue, result)
            else -> formatRegularToken(tokenValue, result)
        }
    }

    private fun formatColon(
        tokenValue: String,
        rulesData: Map<String, Any>,
        result: String,
    ): String {
        val colonBefore = rulesData["colonBefore"] as Boolean
        val colonAfter = rulesData["colonAfter"] as Boolean
        val formattedBefore = handleBeforeSpace(tokenValue, colonBefore, result)
        return handleAfterSpace(formattedBefore, colonAfter)
    }

    private fun formatAssignation(
        tokenValue: String,
        rulesData: Map<String, Any>,
        result: String,
    ): String {
        val equalsBefore = rulesData["assignationBefore"] as Boolean
        val equalsAfter = rulesData["assignationAfter"] as Boolean
        val formattedBefore = handleBeforeSpace(tokenValue, equalsBefore, result)
        return handleAfterSpace(formattedBefore, equalsAfter)
    }

    private fun formatPrintln(
        tokenValue: String,
        rulesData: Map<String, Any>,
        result: String,
    ): String {
        val printJump = rulesData["printJump"] as Int
        if (result.endsWith(" ")) {
            val modifiedResult = result.dropLast(1)
            return modifiedResult + "\n".repeat(printJump) + tokenValue
        } else {
            return result + "\n".repeat(printJump) + tokenValue
        }
    }

    private fun formatOperator(
        tokenValue: String,
        result: String,
    ): String {
        val formattedBefore = handleBeforeSpace(tokenValue, true, result)
        return handleAfterSpace(formattedBefore, true)
    }

    private fun formatSemicolon(
        tokenValue: String,
        result: String,
    ): String {
        return handleBeforeSpace(tokenValue, false, result) + "\n"
    }

    private fun formatRegularToken(
        tokenValue: String,
        result: String,
    ): String {
        return "$result$tokenValue "
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
}
