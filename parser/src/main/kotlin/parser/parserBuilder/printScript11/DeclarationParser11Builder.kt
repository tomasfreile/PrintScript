@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parserBuilder.printScript11

import parser.analysis.semantic.*
import parser.analysis.syntax.*
import parser.analysis.syntax.ifSyntax.IsIfElseSyntax
import parser.nodeBuilder.*
import parser.parser.DeclarationParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder
import token.TokenType

class DeclarationParser11Builder : ParserBuilder {
    override fun build(): Parser {
        return DeclarationParser(
            TokenType.SEMICOLON,
            getTypeMap(),
            getDeclarationList(),
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
            Pair(TokenType.READ_INPUT, FunctionSemantic()),
            Pair(TokenType.READ_ENV, FunctionSemantic()),
        )
    }

    private fun getDeclarationList(): List<TokenType> {
        return listOf(
            TokenType.LET,
            TokenType.CONST,
        )
    }

    private fun getTypeMap(): Map<TokenType, TokenType> {
        return mapOf(
            Pair(TokenType.NUMBER_LITERAL, TokenType.NUMBER_TYPE),
            Pair(TokenType.STRING_LITERAL, TokenType.STRING_TYPE),
            Pair(TokenType.BOOLEAN_LITERAL, TokenType.BOOLEAN_TYPE),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, IsStringSyntax()),
            Pair(TokenType.NUMBER_TYPE, IsArithmeticSyntax()),
            Pair(TokenType.BOOLEAN_TYPE, IsBooleanSyntax()),
            Pair(TokenType.READ_INPUT, IsFunctionSyntax()),
            Pair(TokenType.READ_ENV, IsFunctionSyntax()),
            Pair(TokenType.IF, IsIfElseSyntax()),
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
