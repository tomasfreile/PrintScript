package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import formatter.Formatter
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
import parser.parserBuilder.PrintScriptOnePointZeroParserBuilder
import sca.StaticCodeAnalyzer
import sca.StaticCodeAnalyzerImpl
import java.io.BufferedReader
import java.io.File

class PrintScript : CliktCommand(help = "PrintScript <Operation> <Source> <Version> <Config>") {
    private val operation by argument(help = "the operation to apply to the file: validation, execution, formatting & analyzing")
        .choice("validate", "execute", "format", "analyze")
    private val source by argument(help = "source file").file(mustExist = true)
    private val version by option("-v", help = "a version")
    private val config by option("-c", help = "config file").file(mustExist = true)

    val lexer: Lexer = LexerBuilder().build("1.0")
    val parser: PrintScriptParser = PrintScriptOnePointZeroParserBuilder().build()
    var interpreter: PrintScriptInterpreter = InterpreterBuilder().build("1.0")
    val symbolTable: MutableMap<Variable, Any> = mutableMapOf()

    override fun run() {
        val sentencesList = getSentenceList()
        try {
            when (operation) {
                "execute" -> {
                    executeCode(sentencesList)
                }

                "format" -> {
                    formatCode(sentencesList)
                }

                "analyze" -> {
                    analyzeCode(sentencesList)
                }

                "validate" -> {
                    validateCode(sentencesList)
                }
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    private fun validateCode(sentencesList: List<String>) {
        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            val tree = parser.createAST(tokenList)
        }
        println("Validation successful!")
    }

    private fun executeCode(sentencesList: List<String>) {
        var result: InterpreterResult = Result("")
        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            val tree = parser.createAST(tokenList)
            result = interpreter.interpret(tree, symbolTable) as InterpreterResult
        }
        when (result) {
            is PrintResult -> println(result.toPrint)
            is Result -> Unit // If the result is not a print do nothing.
            is MultipleResults -> TODO()
        }
    }

    private fun formatCode(sentencesList: List<String>) {
        val formatter: Formatter =
            PrintScriptFormatter(config?.path ?: throw NullPointerException("Expected config file path for formatter."))
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
        val sca: StaticCodeAnalyzer =
            StaticCodeAnalyzerImpl(config?.path ?: throw NullPointerException("Expected config file path for sca."))
        var errorList: List<String> = listOf()
        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            val tree = parser.createAST(tokenList)
            errorList = errorList.plus(sca.analyze(tree ?: throw NullPointerException("Null ast tree")))
        }
        for (error in errorList) {
            println(error)
        }
    }

    private fun getSentenceList(): List<String> {
        val bufferedReader: BufferedReader = File(source.path).bufferedReader()
        val file = bufferedReader.use { it.readText() }
        var sentencesList: List<String> = emptyList()
        var sentence: String = ""
        for (char in file) {
            sentence += char
            if (char == ';') {
                sentencesList = sentencesList.plus(sentence)
                sentence = ""
            }
        }
        return sentencesList
    }
}

fun main(args: Array<String>) = PrintScript().main(args)
