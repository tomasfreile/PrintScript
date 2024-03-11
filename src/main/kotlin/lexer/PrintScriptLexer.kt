package org.example.lexer

import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.Token
import org.example.token.TypeEnum

class PrintScriptLexer : Lexer {
    override fun lex(input: String): List<Token> {
        val tokens = ArrayList<Token>()
        var index = 0
        var line = 0
        var column = 0

        fun processString(character: Char) {
            val result = readString(index, input, tokens, line, column, character)
            val charIncrement = result - index
            index += charIncrement
            column += charIncrement
        }

        while (index < input.length) {
            val char = input[index]
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
                    processString('"')
                }
                '\'' -> {
                    processString('\'')
                }
                '\n' -> {
                    line++
                    column = - 1
                }
                ' ' -> {
                    //do nothing
                }
                else -> {
                    when {
                        char.isDigit() -> {
                            val result = readNumber(line, column, index, input, tokens)
                            val charIncrement = result - index
                            index += charIncrement
                            column += charIncrement
                            continue
                        }
                        char.isLetter() -> {
                            val result = readWord(line, column, index, input, tokens)
                            val charIncrement = result - index
                            index += charIncrement
                            column += charIncrement
                            continue
                        }
                        else -> {
                            throw IllegalArgumentException("Unexpected character $char at $line:$index")
                        }
                    }
                }
            }

            index++
            column++
        }
        return tokens
    }

    private fun readString(index: Int, input: String, tokens: MutableList<Token>, line: Int, column: Int, endChar: Char): Int { //returns the index of the endChar
        var endIndex = index + 1
        var endColumn = column
        val stringBuilder = StringBuilder().append(endChar)

        while (endIndex < input.length && input[endIndex] != endChar) {
            stringBuilder.append(input[endIndex])
            endIndex++
            endColumn++
        }
        stringBuilder.append(endChar)
        val string = stringBuilder.toString()

        tokens.add(PrintScriptToken(TypeEnum.STRING, string, Coordinate(line, column), Coordinate(line, endColumn + 1)))

        return endIndex + 1
    }

    private fun readWord(line: Int, column: Int, index: Int, input: String, tokens: MutableList<Token>): Int {  //returns the index of the last character of the word
        var endIndex = index
        var endColumn = column
        val stringBuilder = StringBuilder()

        while (endIndex < input.length && isValidValueIdentifierCharacter(input, endIndex)) {
            stringBuilder.append(input[endIndex])
            endIndex++
            endColumn++
        }
        val identifier = stringBuilder.toString()

        val tokenType = when (identifier) {
            "println" -> TypeEnum.PRINT
            "let" -> TypeEnum.VARIABLE_KEYWORD
            "String" -> TypeEnum.STRING_TYPE
            "Number" -> TypeEnum.NUMBER_TYPE
            else -> TypeEnum.VALUE_IDENTIFIER
        }

        tokens.add(PrintScriptToken(tokenType, identifier, Coordinate(line, column), Coordinate(line, endColumn)))
        return endIndex
    }

    private fun isValidValueIdentifierCharacter(input: String, endIndex: Int) =
        (input[endIndex].isLetterOrDigit() || input[endIndex] == '_')

    private fun readNumber(line: Int, column: Int, index: Int, input: String, tokens: MutableList<Token>): Int { //returns the index of the last character of the number
        var endIndex = index
        var endColumn = column
        val stringBuilder = StringBuilder()

        while (endIndex < input.length && input[endIndex].isDigit()) {
            stringBuilder.append(input[endIndex])
            endIndex++
            endColumn++
        }
        val number = stringBuilder.toString()
        tokens.add(PrintScriptToken(TypeEnum.NUMBER, number, Coordinate(line, column), Coordinate(line, endColumn)))
        return endIndex
    }
}