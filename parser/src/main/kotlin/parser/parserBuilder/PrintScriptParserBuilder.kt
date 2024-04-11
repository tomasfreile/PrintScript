package parser.parserBuilder

import parser.PrintScriptParser

interface PrintScriptParserBuilder {
    fun build(): PrintScriptParser
}
