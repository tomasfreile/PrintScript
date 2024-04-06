package parser

sealed class PrintScriptParserException : Exception()

class InvalidSyntaxException(message: String) : PrintScriptParserException()

class InvalidOperatorException(message: String) : PrintScriptParserException()

class InvalidDeclarationStatement(message: String) : PrintScriptParserException()
