package org.example.interpreter

import org.example.parser.ASTSingleNode
import org.example.parser.Node
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TokenType

class DeclarationInterpreter :Interpreter {
    override fun interpret(
        node: Node?,
        interpreters: Map<TokenType, Interpreter>,
        symbolTable: Map<String, Token>
    ): Any? {
        node as ASTSingleNode
        val valueIdentifier = getValueIdentifier(node)
        val variableType = getVariableType(node)
        val assignationNode = getAssignationNode(node)
        val assignationNodeChild = assignationNode.node
        val variableValue = interpreters[assignationNodeChild?.token?.type]?.interpret(assignationNodeChild, interpreters, symbolTable).toString()
        val newSymbolTable = symbolTable.plus(Pair(valueIdentifier, PrintScriptToken(variableType, variableValue, Coordinate(0,0), Coordinate(0,0))))
        return PrintScriptInterpreter(interpreters, newSymbolTable)

    }

    private fun getValueIdentifier(node: ASTSingleNode): String {
        return node.node?.token?.value ?: throw NullPointerException("Expected VALUE_IDENTIFIER, received null")
    }

    private fun getVariableType(node: ASTSingleNode): TokenType {
        return getNthChildNodeInASTSingleNode(node, 3).token.type
    }

    private fun getAssignationNode(node: ASTSingleNode): ASTSingleNode {
        return getNthChildNodeInASTSingleNode(node, 4)
    }

    private fun getNthChildNodeInASTSingleNode(node:ASTSingleNode, n: Int): ASTSingleNode{
        var child = node
        for(i in 0..<n){
            child = child.node as ASTSingleNode
        }
        return child
    }
}