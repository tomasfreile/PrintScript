package interpreter.result

sealed class InterpreterResult

data class PrintResult(val toPrint: String) : InterpreterResult()

data class Result(val value: Any) : InterpreterResult()
