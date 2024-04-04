package cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.choice
import com.github.ajalt.clikt.parameters.types.file
import interpreter.PrintScriptInterpreter
import interpreter.builder.InterpreterBuilder
import lexer.Lexer
import lexer.PrintScriptLexer
import lexer.getTokenMap
import parser.Parser
import java.io.File

class PrintScript : CliktCommand(help = "PrintScript <Operation> <Source> <Version> <Config>") {
    private val operation by argument(help = "the operation to apply to the file: validation, execution, formatting & analyzing")
        .choice("validation", "execution", "formatting", "analyzing")
    private val source by argument(help = "source file").file(mustExist = true)
    private val version: String by argument(help = "a version")
    private val config by argument().file(mustExist = true)

    val lexer: Lexer = PrintScriptLexer(getTokenMap())
    val parser: Parser = Parser()
    val interpreter: PrintScriptInterpreter = InterpreterBuilder().build()

    override fun run() {
        val lineList = mutableListOf<String>()
        File(source.path).useLines { lines -> lines.forEach { lineList.add(it) } }
        val sentence = lineList[0]
        val tokenList = lexer.lex(sentence)
        val tree = parser.parse(tokenList)
        interpreter.interpret(tree)
    }
}

fun main(args: Array<String>) = PrintScript().main(args)
