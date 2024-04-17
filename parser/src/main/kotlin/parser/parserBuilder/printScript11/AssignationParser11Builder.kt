@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parserBuilder.printScript11

import parser.analysis.semantic.BooleanSemantic
import parser.analysis.semantic.NumberSemantic
import parser.analysis.semantic.SemanticRule
import parser.analysis.semantic.StringSemantic
import parser.analysis.syntax.*
import parser.analysis.syntax.ifSyntax.IsIfElseSyntax
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.BooleanNodeBuilder
import parser.nodeBuilder.NodeBuilder
import parser.nodeBuilder.StringNodeBuilder
import parser.parser.AssignationParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder
import token.TokenType

class AssignationParser11Builder : ParserBuilder {
    override fun build(): Parser {
        return AssignationParser(
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
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, IsStringSyntax()),
            Pair(TokenType.NUMBER_TYPE, IsArithmeticSyntax()),
            Pair(TokenType.BOOLEAN_TYPE, IsBooleanSyntax()),
            Pair(TokenType.IF, IsIfElseSyntax()),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBER_TYPE, ArithmeticNodeBuilder()),
            Pair(TokenType.BOOLEAN_TYPE, BooleanNodeBuilder()),
        )
    }
}
