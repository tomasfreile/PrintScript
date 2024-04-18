package parser.parserBuilder

import parser.parser.Parser

interface ParserBuilder {
    fun build(): Parser
}
