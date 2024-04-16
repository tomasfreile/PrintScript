@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.parserBuilder.printScript11

import parser.analysis.semantic.*
import parser.analysis.syntax.*
import parser.analysis.syntax.ifSyntax.IsIfElseSyntax
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
}
