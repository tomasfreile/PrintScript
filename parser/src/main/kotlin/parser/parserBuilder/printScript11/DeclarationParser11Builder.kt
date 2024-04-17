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
            Pair(TokenType.NUMBERTYPE, NumberSemantic()),
            Pair(TokenType.STRINGTYPE, StringSemantic()),
            Pair(TokenType.BOOLEANTYPE, BooleanSemantic()),
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
            Pair(TokenType.NUMBERLITERAL, TokenType.NUMBERTYPE),
            Pair(TokenType.STRINGLITERAL, TokenType.STRINGTYPE),
            Pair(TokenType.BOOLEANLITERAL, TokenType.BOOLEANTYPE),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.STRINGTYPE, IsStringSyntax()),
            Pair(TokenType.NUMBERTYPE, IsArithmeticSyntax()),
            Pair(TokenType.BOOLEANTYPE, IsBooleanSyntax()),
            Pair(TokenType.IF, IsIfElseSyntax()),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRINGTYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBERTYPE, ArithmeticNodeBuilder()),
            Pair(TokenType.BOOLEANTYPE, BooleanNodeBuilder()),
        )
    }
}
