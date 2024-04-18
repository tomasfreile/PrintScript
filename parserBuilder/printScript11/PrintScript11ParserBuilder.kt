package parser.parserBuilder.printScript11

import parser.PrintScriptParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder

class PrintScript11ParserBuilder : ParserBuilder {
    override fun build(): Parser {
        return PrintScriptParser(getParsers())
    }

    private fun getParsers(): List<Parser> {
        return listOf(
            DeclarationParser11Builder().build(),
            PrintParser11Builder().build(),
            AssignationParser11Builder().build(),
            IfParserBuilder().build(),
        )
    }
}
