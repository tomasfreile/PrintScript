package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.validate
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import formatter.PrintScriptFormatter
import formatter.PrintScriptFormatterBuilder
import interpreter.builder.InterpreterBuilder
import interpreter.interpreter.PrintScriptInterpreter
import interpreter.result.InterpreterResult
import interpreter.result.MultipleResults
import interpreter.result.PrintResult
import interpreter.result.Result
import interpreter.variable.Variable
import lexer.Lexer
import lexer.factory.LexerBuilder
import parser.parser.Parser
import parser.parserBuilder.printScript10.PrintScript10ParserBuilder
import parser.parserBuilder.printScript11.PrintScript11ParserBuilder
import sca.StaticCodeAnalyzerImpl
import token.TokenType
import java.io.File

class PrintScript : CliktCommand(help = "PrintScript <Version> <Operation> <Source> <Config>") {
    private val version by argument(help = "a version")
        .choice("1.0", "1.1")
        .validate { require(it == "1.0" || it == "1.1") { "Invalid version: $it. Supported versions are 1.0 and 1.1" } }

    private val operation by argument(help = "the operation to apply to the file: validation, execution, formatting & analyzing")
        .choice("validate", "execute", "format", "analyze")

    private val source by argument(help = "source file").file(mustExist = true)

    private val config by option("-c", help = "config file").file(mustExist = true)
    private val envFile by option("-e", "--env", help = "environment variables file").file(mustExist = true)

    private lateinit var lexer: Lexer
    private lateinit var parser: Parser
    private lateinit var interpreter: PrintScriptInterpreter
    private val symbolTable: MutableMap<Variable, Any> = mutableMapOf()

    override fun run() {
        lexer = LexerBuilder().build(version)
        parser = buildParser(version)
        interpreter = InterpreterBuilder().build(version)

        val reader = FileReader(source.inputStream(), version)
        try {
            when (operation) {
                "execute" -> executeCode(reader)
                "format" -> formatCode(reader)
                "analyze" -> analyzeCode(reader)
                "validate" -> validateCode(reader)
            }
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    private fun buildParser(version: String): Parser {
        return when (version) {
            "1.0" -> PrintScript10ParserBuilder().build()
            "1.1" -> PrintScript11ParserBuilder().build()
            else -> throw IllegalArgumentException("Invalid version $version. Supported versions are 1.0 and 1.1")
        }
    }

    private fun validateCode(reader: FileReader) {
        while (reader.canContinue()) {
            val statements = reader.getNextLine()
            for (statement in statements) {
                try {
                    parser.createAST(statement)
                } catch (e: Exception) {
                    println("error in parsing: $e")
                }
            }
        }
        println("Validation successful!")
    }

    private fun executeCode(reader: FileReader) {
        if (envFile != null) {
            insertEnvironmentVariablesInSymbolTable()
        }
        while (reader.canContinue()) {
            val statements = reader.getNextLine()

            var result: InterpreterResult
            for (statement in statements) {
                try {
                    val ast = parser.createAST(statement)
                    result = interpreter.interpret(ast, symbolTable) as InterpreterResult
                    printResults(result)
                } catch (e: Exception) {
                    println("error in execution: $e")
                    break
                }
            }
        }
    }

    private fun insertEnvironmentVariablesInSymbolTable() {
        // Read file contents line by line
        val lines = File(envFile!!.path).readLines()

        // Print each line of the file
        lines.forEach {
            val pair = it.split("=")
            symbolTable[Variable(pair[0], TokenType.STRINGTYPE, TokenType.CONST)] = pair[1]
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

    private fun formatCode(reader: FileReader) {
        val formatter =
            PrintScriptFormatterBuilder().build(
                version,
                config?.path ?: throw NullPointerException("Config file null"),
            ) as PrintScriptFormatter
        val file = File(source.path)
        var text = ""
        while (reader.canContinue()) {
            val statements = reader.getNextLine()
            for (statement in statements) {
                try {
                    val ast = parser.createAST(statement)
                    val line = ast?.let { formatter.format(it) }
                    text += line
                } catch (e: Exception) {
                    println("error in formatting: $e")
                }
            }
        }
        file.writeText(text)
    }

    private fun analyzeCode(reader: FileReader) {
        val sca = StaticCodeAnalyzerImpl(requireNotNull(config?.path) { "Expected config file path for sca." }, version)
        val errorList = mutableListOf<String>()
        while (reader.canContinue()) {
            val statements = reader.getNextLine()
            for (statement in statements) {
                try {
                    val ast = parser.createAST(statement)
                    val errors = ast?.let { sca.analyze(it) }
                    errorList += errors ?: emptyList()
                } catch (e: Exception) {
                    println("error in analyzing: $e")
                }
            }
        }
        errorList.forEach(::println)
    }
}

fun main(args: Array<String>) = PrintScript().main(args)
