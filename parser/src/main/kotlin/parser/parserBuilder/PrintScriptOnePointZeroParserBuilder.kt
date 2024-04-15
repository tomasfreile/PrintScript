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
            Pair(TokenType.NUMBERTYPE, NumberSemantic()),
            Pair(TokenType.STRINGTYPE, StringSemantic()),
        )
    }

    private fun getDeclarationList(): List<TokenType> {
        return listOf(
            TokenType.LET,
        )
    }

    private fun getTypeMap(): Map<TokenType, TokenType> {
        return mapOf(
            Pair(TokenType.NUMBERLITERAL, TokenType.NUMBERTYPE),
            Pair(TokenType.STRINGLITERAL, TokenType.STRINGTYPE),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.STRINGTYPE, IsStringSyntax()),
            Pair(TokenType.NUMBERTYPE, IsArithmeticSyntax()),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRINGTYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBERTYPE, ArithmeticNodeBuilder()),
        )
    }
}
