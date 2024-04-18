@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parserBuilder.printScript11

import parser.analysis.semantic.analyser.PrintSemanticAnalyzer
import parser.analysis.semantic.analyser.SemanticAnalyzer
import parser.analysis.semantic.rule.BooleanSemantic
import parser.analysis.semantic.rule.FunctionSemantic
import parser.analysis.semantic.rule.NumberSemantic
import parser.analysis.semantic.rule.SemanticRule
import parser.analysis.semantic.rule.StringSemantic
import parser.analysis.syntax.analyzer.SyntaxAnalyzer
import parser.analysis.syntax.analyzer.SyntaxAnalyzerImpl
import parser.analysis.syntax.rule.*
import parser.analysis.syntax.rule.ifSyntax.IsIfElseSyntax
import parser.nodeBuilder.*
import parser.parser.Parser
import parser.parser.PrintParser
import parser.parserBuilder.ParserBuilder
import token.TokenType

class PrintParser11Builder : ParserBuilder {
    override fun build(): Parser {
        return PrintParser(
            TokenType.SEMICOLON,
            getSyntaxAnalyzer(),
            getSemanticAnalyzer(),
            getNodeBuilders(),
        )
    }

    private fun getSemanticMap(): Map<TokenType, SemanticRule> {
        return mapOf(
            Pair(TokenType.NUMBERTYPE, NumberSemantic()),
            Pair(TokenType.STRINGTYPE, StringSemantic()),
            Pair(TokenType.BOOLEANTYPE, BooleanSemantic()),
            Pair(TokenType.READENV, FunctionSemantic()),
            Pair(TokenType.READINPUT, FunctionSemantic()),
        )
    }

    private fun getSyntaxMap(): Map<TokenType, SyntaxRule> {
        return mapOf(
            Pair(TokenType.STRINGTYPE, IsStringSyntax()),
            Pair(TokenType.NUMBERTYPE, IsArithmeticSyntax()),
            Pair(TokenType.BOOLEANTYPE, IsBooleanSyntax()),
            Pair(TokenType.IF, IsIfElseSyntax()),
            Pair(TokenType.READENV, IsReadEnvFunctionSyntax()),
            Pair(TokenType.READINPUT, IsReadInputFunctionSyntax()),
        )
    }

    private fun getNodeBuilders(): Map<TokenType, NodeBuilder> {
        return mapOf(
            Pair(TokenType.STRINGTYPE, StringNodeBuilder()),
            Pair(TokenType.NUMBERTYPE, ArithmeticNodeBuilder()),
            Pair(TokenType.BOOLEANTYPE, BooleanNodeBuilder()),
            Pair(TokenType.READENV, FunctionNodeBuilder()),
            Pair(TokenType.READINPUT, FunctionNodeBuilder()),
        )
    }

    private fun getSemanticAnalyzer(): SemanticAnalyzer {
        return PrintSemanticAnalyzer(
            mapOf(
                Pair(TokenType.NUMBERTYPE, listOf(NumberSemantic())),
                Pair(TokenType.STRINGTYPE, listOf(StringSemantic(), NumberSemantic())),
                Pair(TokenType.BOOLEANTYPE, listOf(BooleanSemantic())),
                Pair(TokenType.READINPUT, listOf(FunctionSemantic())),
                Pair(TokenType.READENV, listOf(FunctionSemantic())),
            ),
        )
    }

    private fun getSyntaxAnalyzer(): SyntaxAnalyzer {
        return SyntaxAnalyzerImpl(
            mapOf(
                Pair(TokenType.NUMBERTYPE, IsArithmeticSyntax()),
                Pair(TokenType.STRINGTYPE, IsStringSyntax()),
                Pair(TokenType.BOOLEANTYPE, IsBooleanSyntax()),
                Pair(TokenType.READINPUT, IsReadInputFunctionSyntax()),
                Pair(TokenType.READENV, IsReadEnvFunctionSyntax()),
            ),
        )
    }
}
