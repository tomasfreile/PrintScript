@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parserBuilder.printScript11

import parser.analysis.semantic.rule.*
import parser.analysis.syntax.rule.*
import parser.analysis.syntax.rule.ifSyntax.IsIfElseSyntax
import parser.parser.IfParser
import parser.parser.Parser
import parser.parserBuilder.ParserBuilder
import token.TokenType

class IfParserBuilder : ParserBuilder {
    override fun build(): Parser {
        return IfParser(
            getParserList(),
            getCodeBlockSyntax(),
            getCodeBlockSemantic(),
        )
    }

    private fun getParserList(): List<Parser> {
        return listOf(
            DeclarationParser11Builder().build(),
            PrintParser11Builder().build(),
            AssignationParser11Builder().build(),
        )
    }

    private fun getCodeBlockSyntax(): SyntaxRule {
        return CodeBlockSyntax(
            TokenType.SEMICOLON,
        )
    }

    private fun getSyntaxRules(): List<SyntaxRule> {
        val result = mutableListOf<SyntaxRule>()
        for (rule in getSyntaxMap().values) {
            result.add(rule)
        }
        return result.toList()
    }

    private fun getCodeBlockSemantic(): CodeBlockSemantic {
        return CodeBlockSemantic(
            getSyntaxMap(),
            getSemanticMap(),
            TokenType.SEMICOLON,
        )
    }

    private fun getSemanticMap(): Map<TokenType, SemanticRule> {
        return mapOf(
            Pair(TokenType.NUMBERTYPE, NumberSemantic()),
            Pair(TokenType.STRINGTYPE, StringSemantic()),
            Pair(TokenType.BOOLEANTYPE, BooleanSemantic()),
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
}
