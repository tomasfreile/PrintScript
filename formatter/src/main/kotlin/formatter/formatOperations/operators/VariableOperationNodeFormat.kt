package formatter.formatOperations.operators

import ast.AstNode
import ast.VariableDeclarationNode
import formatter.Formatter
import formatter.formatOperations.FormatOperation
import formatter.formatOperations.commons.FormatSemicolon
import formatter.formatOperations.commons.HandleSpace
import token.TokenType

class VariableOperationNodeFormat(
    private val allowedDeclarationTokens: Map<TokenType, String>,
    private val allowedValueTypes: Map<TokenType, String>,
) : FormatOperation {
    private val spaceHandler = HandleSpace()
    private val semicolonHandler = FormatSemicolon()

    override fun canHandle(node: AstNode): Boolean {
        return node is VariableDeclarationNode
    }

    override fun format(
        node: AstNode,
        formatter: Formatter,
    ): String {
        node as VariableDeclarationNode
        val colonBefore = formatter.getRules()["colonBefore"] as Boolean
        val colonAfter = formatter.getRules()["colonAfter"] as Boolean
        val equalsBefore = formatter.getRules()["assignationBefore"] as Boolean
        val equalsAfter = formatter.getRules()["assignationAfter"] as Boolean
        val variableSymbol = defineVariableSymbol(node.declarationType)
        val identifier = variableSymbol + " " + node.identifier
        val colon = spaceHandler.handleSpaces(":", colonBefore, colonAfter, identifier)
        val valueTypeSymbol = defineValueType(node.valueType)
        val valueType = colon + valueTypeSymbol
        val equals = spaceHandler.handleSpaces("=", equalsBefore, equalsAfter, valueType)
        val format = equals + formatter.format(node.expression)
        return semicolonHandler.formatSemicolon(format)
    }

    private fun defineVariableSymbol(declarationType: TokenType): String {
        if (allowedDeclarationTokens.containsKey(declarationType)) {
            return allowedDeclarationTokens[declarationType] as String
        }
        throw UnsupportedOperationException("Unsupported declaration type: $declarationType")
    }

    private fun defineValueType(valueType: TokenType): String {
        if (allowedValueTypes.containsKey(valueType)) {
            return allowedValueTypes[valueType] as String
        }
        throw UnsupportedOperationException("Unsupported value type: $valueType")
    }
}
