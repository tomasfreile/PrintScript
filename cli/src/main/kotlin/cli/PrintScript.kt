package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import formatter.PrintScriptFormatter
import interpreter.PrintScriptInterpreter
import interpreter.builder.InterpreterBuilder
import interpreter.result.InterpreterResult
import interpreter.result.MultipleResults
import interpreter.result.PrintResult
import interpreter.result.Result
import interpreter.variable.Variable
import lexer.Lexer
import lexer.factory.LexerBuilder
import parser.PrintScriptParser
import parser.parserBuilder.PrintScriptOnePointOneParserBuilder
import parser.parserBuilder.PrintScriptOnePointZeroParserBuilder
import sca.StaticCodeAnalyzerImpl
import java.io.BufferedReader
import java.io.File

class PrintScript : CliktCommand(help = "PrintScript <Version> <Operation> <Source> <Config>") {
    private val version by argument(help = "a version")
        .choice("1.0", "1.1")
        .validate { require(it == "1.0" || it == "1.1") { "Invalid version: $it. Supported versions are 1.0 and 1.1" } }

    private val operation by argument(help = "the operation to apply to the file: validation, execution, formatting & analyzing")
        .choice("validate", "execute", "format", "analyze")

    private val source by argument(help = "source file").file(mustExist = true)

    private val config by option("-c", help = "config file").file(mustExist = true)

    private lateinit var lexer: Lexer
    private lateinit var parser: PrintScriptParser
    private lateinit var interpreter: PrintScriptInterpreter
    private val symbolTable: MutableMap<Variable, Any> = mutableMapOf()

    override fun run() {
        lexer = LexerBuilder().build(version)
        parser = buildParser(version)
        interpreter = InterpreterBuilder().build(version)

        val sentencesList = getSentenceList()
        try {
            when (operation) {
                "execute" -> executeCode(sentencesList)
                "format" -> formatCode(sentencesList)
                "analyze" -> analyzeCode(sentencesList)
                "validate" -> validateCode(sentencesList)
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    private fun buildParser(version: String): PrintScriptParser {
        return when (version) {
            "1.0" -> PrintScriptOnePointZeroParserBuilder().build()
            "1.1" -> PrintScriptOnePointOneParserBuilder().build()
            else -> throw IllegalArgumentException("Invalid version $version. Supported versions are 1.0 and 1.1")
        }
    }

    private fun validateCode(sentencesList: List<String>) {
        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            parser.createAST(tokenList)
        }
        println("Validation successful!")
    }

    private fun executeCode(sentencesList: List<String>) {
        var result: InterpreterResult
        try {
            for (sentence in sentencesList) {
                try {
                    val tokenList = lexer.lex(sentence)
                    try {
                        val tree = parser.createAST(tokenList)
                        try {
                            result = interpreter.interpret(tree, symbolTable) as InterpreterResult
                            printResults(result)
                        } catch (e: Exception) {
                            println("error in interpreting: $e")
                        }
                    } catch (e: Exception) {
                        println("error in parsing: $e")
                    }
                } catch (e: Exception) {
                    println("error in lexing: $e")
                }
            }
        } catch (e: Exception) {
            println("error in execution: $e")
        }
    }

    private fun printResults(result: InterpreterResult) {
        when (result) {
            is PrintResult -> println(result.toPrint)
            is Result -> Unit // If the result is not a print do nothing.
            is MultipleResults -> for (subResult in result.values) {
                printResults(subResult)
            } // Run this function for multiple results.
        }
    }

    private fun formatCode(sentencesList: List<String>) {
        val formatter = PrintScriptFormatter(requireNotNull(config?.path) { "Expected config file path for formatter." })
        val file = File(source.path)
        var text = ""
        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            val tree = parser.createAST(tokenList)
            val line = tree?.let { formatter.format(it) }
            text += line
        }
        file.writeText(text)
    }

    private fun analyzeCode(sentencesList: List<String>) {
        val sca = StaticCodeAnalyzerImpl(requireNotNull(config?.path) { "Expected config file path for sca." })
        var errorList = emptyList<String>()
        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            val tree = parser.createAST(tokenList)
            errorList += sca.analyze(tree ?: throw NullPointerException("Null AST tree"))
        }
        errorList.forEach(::println)
    }

    private fun getSentenceList(): List<String> {
        val reader = source.bufferedReader()
        val sentencesList = mutableListOf<String>()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            getLine(reader, line!!)?.let { sentencesList.add(it) }
        }
        return sentencesList
    }

    private fun readIfBlock(
        reader: BufferedReader,
        line: String,
    ): String {
        var result = line
        val ifLine = line
        val ifBlock = reader.readLine()
        val line3 = reader.readLine()

        if (line3 != null && line3.trim() == "} else {") {
            val elseBlock = reader.readLine()
            val elseEnd = reader.readLine()
            result = ifLine + ifBlock + line3 + elseBlock + elseEnd
        } else {
            result = ifLine + ifBlock + line3
        }
        return result
    }

    private fun isIfStatement(line: String): Boolean {
        return line.startsWith("if(") || line.startsWith("if (")
    }

    private fun getLine(
        reader: BufferedReader,
        line: String,
    ): String? {
        if (line.isBlank()) {
            return null // Skip empty lines
        }
        var processedLine = line
        if (isIfStatement(line)) {
            processedLine = readIfBlock(reader, line)
        }
        println("Preprocessed line: $processedLine")
        return processedLine
    }
}

fun main(args: Array<String>) = PrintScript().main(args)
