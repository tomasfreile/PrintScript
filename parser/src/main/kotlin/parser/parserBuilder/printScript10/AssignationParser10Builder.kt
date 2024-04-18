package parser.parserBuilder.printScript10

import parser.analysis.semantic.analyser.AssignationSemanticAnalyzer
import parser.analysis.semantic.analyser.SemanticAnalyzer
import parser.analysis.semantic.rule.NumberSemantic
import parser.analysis.semantic.rule.StringSemantic
import parser.analysis.syntax.analyzer.SyntaxAnalyzer
import parser.analysis.syntax.analyzer.SyntaxAnalyzerImpl
import parser.analysis.syntax.rule.IsArithmeticSyntax
import parser.analysis.syntax.rule.IsStringSyntax
import parser.analysis.syntax.rule.SyntaxRule
import parser.nodeBuilder.ArithmeticNodeBuilder
import parser.nodeBuilder.NodeBuilder
import parser.nodeBuilder.StringNodeBuilder
import parser.parser.AssignationParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder
import token.TokenType

class AssignationParser10Builder : ParserBuilder {
    override fun build(): Parser {
        return AssignationParser(
            TokenType.SEMICOLON,
            getSyntaxAnalyzer(),
            getSemanticAnalyzer(),
            getNodeBuilders(),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.NUMBERTYPE, IsArithmeticSyntax()),
            Pair(TokenType.STRINGTYPE, IsStringSyntax()),
        )
    }

    private fun getSemanticAnalyzer(): SemanticAnalyzer {
        return AssignationSemanticAnalyzer(
            mapOf(
                Pair(TokenType.NUMBERTYPE, listOf(NumberSemantic())),
                Pair(TokenType.STRINGTYPE, listOf(StringSemantic())),
            ),
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
