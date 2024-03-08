package org.example

import org.example.lexer.PrintScriptLexer

fun main() {
    println("Hello World!")

    val lexer = PrintScriptLexer()
    val tokens = lexer.lex("print('Hello World!')")

    tokens.forEach {
        println(it.type)
        println(it.value)

    }
}