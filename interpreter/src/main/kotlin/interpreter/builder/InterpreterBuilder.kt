package interpreter.builder

import interpreter.AssignationInterpreter
import interpreter.DeclarationInterpreter
import interpreter.NumberInterpreter
import interpreter.OperationInterpreter
import interpreter.ParenInterpreter
import interpreter.PrintInterpreter
import interpreter.PrintScriptInterpreter
import interpreter.StringInterpreter
import interpreter.ValueIdentifierInterpreter
import interpreter.operation.MinusOperation
import interpreter.operation.PlusOperation
import interpreter.operation.SlashOperation
import interpreter.operation.StarOperation
import token.Token
import token.TokenType

class InterpreterBuilder {
    fun build(): PrintScriptInterpreter {
        val operationInterpreter =
            OperationInterpreter(listOf(PlusOperation(), MinusOperation(), SlashOperation(), StarOperation()))

        val interpreterMap =
            mapOf(
                Pair(TokenType.LEFT_PAREN, ParenInterpreter()),
                Pair(TokenType.RIGHT_PAREN, ParenInterpreter()),
                Pair(TokenType.STRING, StringInterpreter()),
                Pair(TokenType.PRINT, PrintInterpreter()),
                Pair(TokenType.NUMBER, NumberInterpreter()),
                Pair(TokenType.STAR, operationInterpreter),
                Pair(TokenType.SLASH, operationInterpreter),
                Pair(TokenType.PLUS, operationInterpreter),
                Pair(TokenType.MINUS, operationInterpreter),
                Pair(TokenType.VARIABLE_KEYWORD, DeclarationInterpreter()),
                Pair(TokenType.VALUE_IDENTIFIER, ValueIdentifierInterpreter()),
                Pair(TokenType.ASSIGNATION, AssignationInterpreter()),
            )

        val symbolTable = emptyMap<String, Token>()

        return PrintScriptInterpreter(interpreterMap, symbolTable)
    }
}
