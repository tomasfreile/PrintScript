@file:Suppress("ktlint:standard:no-wildcard-imports")

package interpreter.builder

import interpreter.function.ReadEnvFunction
import interpreter.function.ReadInputFunction
import interpreter.interpreter.*
import interpreter.literal.Boolean
import interpreter.literal.Number
import interpreter.literal.String
import interpreter.operation.MinusOperation
import interpreter.operation.PlusOperation
import interpreter.operation.SlashOperation
import interpreter.operation.StarOperation

class InterpreterBuilder {
    fun build(version: kotlin.String): PrintScriptInterpreter {
        when (version) {
            "1.0" -> {
                val operationInterpreter =
                    OperationInterpreter(listOf(PlusOperation(), MinusOperation(), SlashOperation(), StarOperation()))
                val literalInterpreter = LiteralInterpreter(listOf(Number(), String()))
                val interpreters =
                    listOf(
                        operationInterpreter,
                        literalInterpreter,
                        AssignationInterpreter(),
                        DeclarationInterpreter(),
                        PrintInterpreter(),
                        NilInterpreter(),
                    )
                return PrintScriptInterpreter(interpreters)
            }
            "1.1" -> {
                val operationInterpreter =
                    OperationInterpreter(listOf(PlusOperation(), MinusOperation(), SlashOperation(), StarOperation()))
                val literalInterpreter = LiteralInterpreter(listOf(Number(), String(), Boolean()))
                val functionInterpreter = FunctionInterpreter(listOf(ReadEnvFunction(), ReadInputFunction()))
                val interpreters =
                    listOf(
                        operationInterpreter,
                        literalInterpreter,
                        AssignationInterpreter(),
                        DeclarationInterpreter(),
                        PrintInterpreter(),
                        ConditionalInterpreter(),
                        functionInterpreter,
                        NilInterpreter(),
                        CodeBlockInterpreter(),
                    )
                return PrintScriptInterpreter(interpreters)
            }
            else -> {
                throw IllegalArgumentException("Unknown version $version")
            }
        }
    }
}
