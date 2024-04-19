package interpreter.interpreter

import ast.AstNode
import ast.VariableDeclarationNode
import interpreter.result.PrintResult
import interpreter.result.PromptResult
import interpreter.result.Result
import interpreter.variable.Variable

class DeclarationInterpreter : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as VariableDeclarationNode
        val identifier = node.identifier
        val valueType = node.valueType
        val declarationType = node.declarationType
        val value = interpreter.interpret(node.expression, symbolTable)
        val variable = Variable(identifier, valueType, declarationType)
        symbolTable[variable] = value
        return when (value) {
            is PrintResult -> {
//                if(variable.valueType != TokenType.STRINGTYPE) throw InvalidValueTypeException("Unexpected value type ${valueType.name} for variable of type ${variable.valueType}")
                PromptResult(variable, value)
            }
            else -> Result(value)
        }
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is VariableDeclarationNode
    }
}
