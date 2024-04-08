package interpreter.builder

import interpreter.AssignationInterpreter
import interpreter.DeclarationInterpreter
import interpreter.LiteralInterpreter
import interpreter.OperationInterpreter
import interpreter.PrintInterpreter
import interpreter.PrintScriptInterpreter
import interpreter.literal.Number
import interpreter.literal.String
import interpreter.operation.MinusOperation
import interpreter.operation.PlusOperation
import interpreter.operation.SlashOperation
import interpreter.operation.StarOperation

class InterpreterBuilder {
    fun build(): PrintScriptInterpreter {
        val operationInterpreter =
            OperationInterpreter(listOf(PlusOperation(), MinusOperation(), SlashOperation(), StarOperation()))
        val literalInterpreter = LiteralInterpreter(listOf(Number(), String()))
        val interpreters =
            listOf(operationInterpreter, literalInterpreter, AssignationInterpreter(), DeclarationInterpreter(), PrintInterpreter())
        return PrintScriptInterpreter(interpreters)
    }
}
