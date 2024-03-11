package org.example.interpreter

import org.example.parser.Node

interface Interpreter {
    fun interpret(ast: Node?): Unit
}