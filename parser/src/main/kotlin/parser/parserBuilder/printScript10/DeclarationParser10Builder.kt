package parser.parserBuilder.printScript10

import parser.analysis.semantic.analyser.DeclarationSemanticAnalyzer
import parser.analysis.semantic.analyser.SemanticAnalyzer
import parser.analysis.semantic.rule.NumberSemantic
import parser.analysis.semantic.rule.StringSemantic
import parser.analysis.syntax.analyzer.SyntaxAnalyzer
import parser.analysis.syntax.analyzer.SyntaxAnalyzerImpl
import parser.analysis.syntax.rule.IsArithmeticSyntax
import parser.analysis.syntax.rule.IsStringSyntax
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.NodeBuilder
import parser.nodeBuilder.StringNodeBuilder
import parser.parser.DeclarationParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder
import token.TokenType

class DeclarationParser10Builder : ParserBuilder {
    override fun build(): Parser {
        return DeclarationParser(
            TokenType.SEMICOLON,
            getTypeMap(),
            getDeclarationList(),
            getSyntaxAnalyzer(),
            getSemanticAnalyzer(),
            getNodeBuilders(),
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

    private fun getSemanticAnalyzer(): SemanticAnalyzer {
        return DeclarationSemanticAnalyzer(
            mapOf(
                Pair(TokenType.NUMBERTYPE, listOf(NumberSemantic())),
                Pair(TokenType.STRINGTYPE, listOf(StringSemantic())),
            ),
            getValidDataTypes(),
        )
    }

    private fun getValidDataTypes(): Map<TokenType, List<TokenType>> {
        return mapOf(
            Pair(TokenType.NUMBERTYPE, listOf(TokenType.NUMBERTYPE)),
            Pair(TokenType.STRINGTYPE, listOf(TokenType.STRINGTYPE)),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRINGTYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBERTYPE, ArithmeticNodeBuilder()),
        )
    }

    private fun getSyntaxAnalyzer(): SyntaxAnalyzer {
        return SyntaxAnalyzerImpl(
            mapOf(
                Pair(TokenType.NUMBERTYPE, IsArithmeticSyntax()),
                Pair(TokenType.STRINGTYPE, IsStringSyntax()),
            ),
        )
    }
}
