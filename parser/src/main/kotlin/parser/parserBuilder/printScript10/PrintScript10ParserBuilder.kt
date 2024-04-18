package parser.parserBuilder.printScript10

import parser.PrintScriptParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder

class PrintScript10ParserBuilder : ParserBuilder {
    override fun build(): Parser {
        return PrintScriptParser(getParsers())
    }

    private fun getParsers(): List<Parser> {
        return listOf(
            DeclarationParser10Builder().build(),
            PrintParser10Builder().build(),
            AssignationParser10Builder().build(),
        )
    }
}
