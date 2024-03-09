package org.example

import org.example.lexer.PrintScriptLexer

fun main() {
    println("Hello World!")

    val lexer = PrintScriptLexer()
    val tokens = lexer.lex("println('Hello World!') let function = 123 + 2 * 3 - 4 / 5 println(function)")

    tokens.forEach {
        println("${it.type} -> ${it.value} in position (${it.start.row}, ${it.start.column}) to (${it.end.row}, ${it.end.column})")

    }
}