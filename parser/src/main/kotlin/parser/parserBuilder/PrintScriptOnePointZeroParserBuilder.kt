package parser.parserBuilder

import parser.PrintScriptParser
import parser.analysis.semantic.NumberSemantic
import parser.analysis.semantic.SemanticRule
import parser.analysis.semantic.StringSemantic
import parser.analysis.syntax.IsArithmeticSyntax
import parser.analysis.syntax.IsStringSyntax
import parser.analysis.syntax.SyntaxRule
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.NodeBuilder
import parser.nodeBuilder.StringNodeBuilder
import parser.parser.AssignationParser
import parser.parser.DeclarationParser
import parser.parser.Parser
import parser.parser.PrintParser
import token.TokenType

class PrintScriptOnePointZeroParserBuilder : PrintScriptParserBuilder {
    override fun build(): PrintScriptParser {
        return PrintScriptParser(getParsers())
    }

    private fun getParsers(): List<Parser> {
        return listOf(
            DeclarationParser(TokenType.SEMICOLON, getTypeMap(), getDeclarationList(), getSyntaxMap(), getSemanticMap(), getNodeBuilders()),
            PrintParser(TokenType.SEMICOLON, getSyntaxMap(), getSemanticMap(), getNodeBuilders()),
            AssignationParser(TokenType.SEMICOLON, getSyntaxMap(), getSemanticMap(), getNodeBuilders()),
        )
    }

    private fun getSemanticMap(): Map<TokenType, SemanticRule> {
        return mapOf(
            Pair(TokenType.NUMBER_TYPE, NumberSemantic()),
            Pair(TokenType.STRING_TYPE, StringSemantic()),
        )
    }

    private fun getDeclarationList(): List<TokenType> {
        return listOf(
            TokenType.LET,
        )
    }

    private fun getTypeMap(): Map<TokenType, TokenType> {
        return mapOf(
            Pair(TokenType.NUMBER_LITERAL, TokenType.NUMBER_TYPE),
            Pair(TokenType.STRING_LITERAL, TokenType.STRING_TYPE),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, IsStringSyntax()),
            Pair(TokenType.NUMBER_TYPE, IsArithmeticSyntax()),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRING_TYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBER_TYPE, ArithmeticNodeBuilder()),
        )
    }
}
