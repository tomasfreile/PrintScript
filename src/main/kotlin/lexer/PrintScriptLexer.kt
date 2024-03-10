package org.example.lexer

import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TypeEnum

class PrintScriptLexer : Lexer {
    override fun lex(input: String): List<Token> {
        val tokens = ArrayList<Token>()
        var column = 0
        var line = 0
        while (column < input.length) {
            val char = input[column]
            when (char) {
                '(' -> {
                    tokens.add(PrintScriptToken(TypeEnum.LEFT_PAREN, "(", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                ')' -> {
                    tokens.add(PrintScriptToken(TypeEnum.RIGHT_PAREN, ")", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                '+' -> {
                    tokens.add(PrintScriptToken(TypeEnum.PLUS, "+", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                '-' -> {
                    tokens.add(PrintScriptToken(TypeEnum.MINUS, "-", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                '*' -> {
                    tokens.add(PrintScriptToken(TypeEnum.STAR, "*", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                '/' -> {
                    tokens.add(PrintScriptToken(TypeEnum.SLASH, "/", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                ';' -> {
                    tokens.add(PrintScriptToken(TypeEnum.SEMICOLON, ";", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                ':' -> {
                    tokens.add(PrintScriptToken(TypeEnum.COLON, ":", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                '=' -> {
                    tokens.add(PrintScriptToken(TypeEnum.ASSIGNATION, "=", Coordinate(line, column), Coordinate(line, column + 1)))
                }
                '"' -> {
                    column = readString(column, input, tokens, line, '"')
                    continue
                }
                '\'' -> {
                    column = readString(column, input, tokens, line, '\'')
                    continue
                }
                ' ' -> {
                }
                '\n' -> {
                    line++
                }
                else -> {
                    when {
                        char.isDigit() -> {
                            column = readNumber(line, column, input, tokens)
                            continue
                        }
                        char.isLetter() -> {
                            column = readWord(line, column, input, tokens)
                            continue
                        }
                        else -> {
                            throw IllegalArgumentException("Unexpected character $char at $line:$column")
                        }
                    }
                }
            }
            column++
        }
        return tokens
    }

    private fun readString(column: Int, input: String, tokens: MutableList<Token>, line: Int, endChar: Char): Int {
        var endIndex = column + 1
        val startIndex = endIndex

        while (endIndex < input.length && input[endIndex] != endChar) {
            endIndex++
        }
        val string = input.substring(startIndex, endIndex)
        tokens.add(PrintScriptToken(TypeEnum.STRING, string, Coordinate(line, startIndex), Coordinate(line, endIndex)))

        return endIndex + 1
    }

    private fun readWord(line: Int, column: Int, input: String, tokens: MutableList<Token>): Int {
        var endIndex = column
        val startIndex = endIndex
        while (endIndex < input.length && isValidValueIdentifierCharacter(input, endIndex)) {
            endIndex++
        }
        val identifier = input.substring(startIndex, endIndex)

        val tokenType = when (identifier) {
            "println" -> TypeEnum.PRINT
            "let" -> TypeEnum.VARIABLE_KEYWORD
            "String" -> TypeEnum.STRING_TYPE
            "Number" -> TypeEnum.NUMBER_TYPE
            else -> TypeEnum.VALUE_IDENTIFIER
        }

        tokens.add(PrintScriptToken(tokenType, identifier, Coordinate(line, startIndex), Coordinate(line, endIndex)))
        return endIndex
    }

    private fun isValidValueIdentifierCharacter(input: String, endIndex: Int) =
        (input[endIndex].isLetter() || input[endIndex].isDigit() || input[endIndex] == '_')

    private fun readNumber(line: Int, column: Int, input: String, tokens: MutableList<Token>): Int {
        var endIndex = column
        val startIndex = endIndex

        while (endIndex < input.length && input[endIndex].isDigit()) {
            endIndex++
        }
        val number = input.substring(startIndex, endIndex)
        tokens.add(PrintScriptToken(TypeEnum.NUMBER, number, Coordinate(line, startIndex), Coordinate(line, endIndex)))
        return endIndex
    }


}