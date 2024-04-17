package interpreter.variable

import token.TokenType

class Variable(val name: String, val valueType: TokenType, val declarationType: TokenType)
