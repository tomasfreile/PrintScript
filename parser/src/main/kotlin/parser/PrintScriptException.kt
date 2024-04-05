package parser

sealed class PrintScriptException : Exception()

class InvalidSyntaxException(message: String) : PrintScriptException()

class InvalidOperatorException(message: String) : PrintScriptException()
