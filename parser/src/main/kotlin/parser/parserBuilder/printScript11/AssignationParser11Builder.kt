@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parserBuilder.printScript11

import parser.analysis.semantic.analyser.AssignationSemanticAnalyzer
import parser.analysis.semantic.analyser.SemanticAnalyzer
import parser.analysis.semantic.rule.*
import parser.analysis.syntax.analyzer.SyntaxAnalyzer
import parser.analysis.syntax.analyzer.SyntaxAnalyzerImpl
import parser.analysis.syntax.rule.*
import parser.analysis.syntax.rule.ifSyntax.IsIfElseSyntax
import parser.nodeBuilder.*
import parser.parser.AssignationParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder
import token.TokenType

class AssignationParser11Builder : ParserBuilder {
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
        return AssignationSemanticAnalyzer(
            mapOf(
                Pair(TokenType.NUMBERTYPE, listOf(NumberSemantic())),
                Pair(TokenType.STRINGTYPE, listOf(StringSemantic())),
                Pair(TokenType.BOOLEANTYPE, listOf(BooleanSemantic())),
                Pair(TokenType.READENV, listOf(FunctionSemantic())),
                Pair(TokenType.READINPUT, listOf(FunctionSemantic())),
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
                Pair(TokenType.IF, IsIfElseSyntax()),
            ),
        )
    }
}
