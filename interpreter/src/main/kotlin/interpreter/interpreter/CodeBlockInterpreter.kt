package interpreter.interpreter

import ast.AstNode
import ast.CodeBlock
import interpreter.result.InterpreterResult
import interpreter.result.MultipleResults
import interpreter.variable.Variable

class CodeBlockInterpreter : Interpreter {
    override fun interpret(
        node: AstNode?,
        interpreter: PrintScriptInterpreter,
        symbolTable: MutableMap<Variable, Any>,
    ): Any {
        node as CodeBlock
        var resultList = listOf<InterpreterResult>()
        for (node in node.nodes) {
            resultList = resultList.plus(interpreter.interpret(node, symbolTable) as InterpreterResult)
        }
        return MultipleResults(resultList)
    }

    override fun canHandle(node: AstNode): Boolean {
        return node is CodeBlock
    }
}
