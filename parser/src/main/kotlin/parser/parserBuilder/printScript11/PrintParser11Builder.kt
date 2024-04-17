@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parserBuilder.printScript11

import parser.analysis.semantic.*
import parser.analysis.syntax.*
import parser.analysis.syntax.ifSyntax.IsIfElseSyntax
import parser.nodeBuilder.*
import parser.parser.Parser
import parser.parser.PrintParser
import parser.parserBuilder.ParserBuilder
import token.Token
import token.TokenType

class PrintParser11Builder : ParserBuilder {
    override fun build(): Parser {
        return PrintParser(
            TokenType.SEMICOLON,
            getSyntaxMap(),
            getSemanticMap(),
            getNodeBuilders(),
        )
    }

    private fun getSemanticMap(): Map<TokenType, SemanticRule> {
        return mapOf(
            Pair(TokenType.NUMBER_TYPE, NumberSemantic()),
            Pair(TokenType.STRING_TYPE, StringSemantic()),
            Pair(TokenType.BOOLEAN_TYPE, BooleanSemantic()),
            Pair(TokenType.READ_ENV, FunctionSemantic()),
            Pair(TokenType.READ_INPUT, FunctionSemantic()),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, IsStringSyntax()),
            Pair(TokenType.NUMBER_TYPE, IsArithmeticSyntax()),
            Pair(TokenType.BOOLEAN_TYPE, IsBooleanSyntax()),
            Pair(TokenType.IF, IsIfElseSyntax()),
            Pair(TokenType.READ_ENV, IsFunctionSyntax()),
            Pair(TokenType.READ_INPUT, IsFunctionSyntax()),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBER_TYPE, ArithmeticNodeBuilder()),
            Pair(TokenType.BOOLEAN_TYPE, BooleanNodeBuilder()),
            Pair(TokenType.READ_ENV, FunctionNodeBuilder()),
            Pair(TokenType.READ_INPUT, FunctionNodeBuilder()),
        )
    }
}
