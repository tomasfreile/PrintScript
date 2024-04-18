package parser.parserBuilder

import parser.parser.Parser
import parser.parserBuilder.printScript10.PrintScript10ParserBuilder
import parser.parserBuilder.printScript11.PrintScript11ParserBuilder

class PrintScriptParserBuilder {
    fun build(version: String): Parser {
        return when (version) {
            "1.0" -> PrintScript10ParserBuilder().build()
            else -> PrintScript11ParserBuilder().build()
        }
    }
}