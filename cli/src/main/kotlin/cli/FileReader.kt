package cli

import lexer.factory.LexerBuilder
import token.Token
import token.TokenType
import java.io.BufferedReader
import java.io.InputStream

class FileReader(
    src: InputStream,
    version: String,
) {
    private var unassignedTokens = emptyList<Token>()
    private var line = 0
    private val reader = BufferedReader(src.bufferedReader())
    private val lineReader: LineReader = LineReader(version)

    fun getNextLine(): List<List<Token>> {
        val line = reader.readLine() ?: return emptyList()
        val lineReaderOutput = lineReader.read(line, this.line, unassignedTokens)
        unassignedTokens = lineReaderOutput.second
        this.line++
        return lineReaderOutput.first
    }

    fun hasNextLine(): Boolean {
        reader.mark(10000)
        val hasNextLine = reader.readLine() != null
        reader.reset()
        return hasNextLine
    }
}

class LineReader(
    version: String,
) {
    private val lexer = LexerBuilder().build(version)

    fun read(
        line: String,
        lineIndex: Int,
        prevTokens: List<Token> = emptyList(),
    ): Pair<List<List<Token>>, List<Token>> {
        val lineTokens = lexer.lexLine(line, lineIndex)
        val totalTokens = prevTokens + lineTokens
        return getStatementsAndRemainingTokens(totalTokens)
    }

    private fun getStatementsAndRemainingTokens(
        tokens: List<Token>,
        endCondition: EndCondition = SemicolonEndCondition(),
        statements: MutableList<List<Token>> = mutableListOf(),
        currentStatement: MutableList<Token> = mutableListOf(),
    ): Pair<List<List<Token>>, List<Token>> {
        var condition = endCondition
        var index = 0
        while (index < tokens.size) {
            val token = tokens[index]
            when {
                token.type == TokenType.IF -> {
                    val result = handleIfStatement(currentStatement, token, tokens, index, statements)
                    return result
                }
                condition.isStatementEnd(token) -> {
                    currentStatement.add(token)
                    if (condition is RightBraceEndCondition && thereAreTokensLeft(index, tokens) && nextTokenIsElseBlock(tokens, index)) {
                        index++
                        continue
                    }
                    statements.add(currentStatement.toList())
                    currentStatement.clear()
                    condition = SemicolonEndCondition()
                }
                else -> currentStatement.add(token)
            }
            index++
        }
        return Pair(statements, currentStatement)
    }

    private fun thereAreTokensLeft(
        i: Int,
        tokens: List<Token>,
    ) = (i < tokens.size - 1)

    private fun handleIfStatement(
        currentStatement: MutableList<Token>,
        token: Token,
        tokens: List<Token>,
        index: Int,
        statements: MutableList<List<Token>>,
    ): Pair<List<List<Token>>, List<Token>> {
        currentStatement.add(token)
        return getStatementsAndRemainingTokens(
            tokens.subList(index + 1, tokens.size),
            RightBraceEndCondition(),
            statements,
            currentStatement,
        )
    }

    private fun nextTokenIsElseBlock(
        tokens: List<Token>,
        index: Int,
    ) = tokens.getOrNull(index + 1)?.type == TokenType.ELSE
}

interface EndCondition {
    fun isStatementEnd(token: Token): Boolean
}

class SemicolonEndCondition : EndCondition {
    override fun isStatementEnd(token: Token): Boolean {
        return token.type == TokenType.SEMICOLON
    }
}

class RightBraceEndCondition : EndCondition {
    override fun isStatementEnd(token: Token): Boolean {
        return token.type == TokenType.RIGHTBRACE
    }
}
