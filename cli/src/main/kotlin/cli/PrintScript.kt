package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import formatter.Formatter
import interpreter.PrintScriptInterpreter
import interpreter.builder.InterpreterBuilder
import lexer.Lexer
import lexer.PrintScriptLexer
import lexer.getTokenMap
import parser.Parser
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

    val lexer: Lexer = PrintScriptLexer(getTokenMap())
    val parser: Parser = Parser()
    var interpreter: PrintScriptInterpreter = InterpreterBuilder().build()

    override fun run() {
        val sentencesList = getSentenceList()
        when (operation) {
            "execute" -> {
                executeCode(sentencesList)
            }

            "format" -> {
                formatCode(sentencesList)
            }

            "analyze" -> {
                val sca: StaticCodeAnalyzer =
                    StaticCodeAnalyzerImpl(config?.path ?: throw NullPointerException("Expected config file path for sca."))
                // TODO
            }

            "validate" -> {
                // TODO
            }
        }
    }

    private fun executeCode(sentencesList: List<String>) {
        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            val tree = parser.parse(tokenList)
            interpreter = interpreter.interpret(tree)
        }
    }

    private fun formatCode(sentencesList: List<String>) {
        val formatter: Formatter = Formatter(config?.path ?: throw NullPointerException("Expected config file path for formatter."))
        val file = File(source.path)
        var text = ""

        for (sentence in sentencesList) {
            val tokenList = lexer.lex(sentence)
            val tree = parser.parse(tokenList)
            val line = formatter.format(tree)
            text += line
        }
        file.writeText(text)
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
