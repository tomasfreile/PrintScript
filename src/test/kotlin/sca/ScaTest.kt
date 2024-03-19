package sca

import org.example.parser.ASTBinaryNode
import org.example.parser.ASTSingleNode
import org.example.sca.rules.PrintShouldNotContainExpressions
import org.example.sca.StaticCodeAnalyzerImpl
import org.example.sca.rules.VariableNamesShouldBeCamelCase
import org.example.sca.rules.VariableNamesShouldBeSnakeCase
import org.example.token.Coordinate
import org.example.token.PrintScriptToken
import org.example.token.TokenType
import org.example.utils.printAST
import org.junit.jupiter.api.Test

class ScaTest {

    private val printRule = PrintShouldNotContainExpressions()
    private val camelCaseRule = VariableNamesShouldBeCamelCase()
    private val snakeCaseRule = VariableNamesShouldBeSnakeCase()
    private val printSca = StaticCodeAnalyzerImpl(listOf(printRule))
    private val camelCaseSca = StaticCodeAnalyzerImpl(listOf(camelCaseRule))
    private val snakeCaseSca = StaticCodeAnalyzerImpl(listOf(snakeCaseRule))

    @Test
    fun `should return empty when no rules are set`(){
        val sca = StaticCodeAnalyzerImpl(listOf())
        val ast =
            ASTSingleNode(null, PrintScriptToken(TokenType.PRINT, "println", Coordinate(2,3), Coordinate(2, 3)))
        assert(sca.analyze(ast).isEmpty())
    }


    @Test
    fun shouldReturnEmptyWhenPrintDoesNotHaveExpressions(){
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null, PrintScriptToken(TokenType.NUMBER, "1", Coordinate(2, 3), Coordinate(2, 3))
                ), PrintScriptToken(TokenType.PRINT, "println", Coordinate(2,3), Coordinate(2, 3))
            )
        val result = printSca.analyze(ast)
        assert(result.isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenPrintContainsNumericOperation(){
        val ast =
            ASTSingleNode(
                ASTBinaryNode(
                    ASTSingleNode(
                        null, PrintScriptToken(TokenType.NUMBER, "1", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    ASTSingleNode(
                        null, PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2,3), Coordinate(2, 3))
                )

        val result = printSca.analyze(ast)
        assert(result.size == 1)
    }

    @Test
    fun shouldReturnWarningWhenPrintContainsMultipleNumericOperations(){
        val ast =
            ASTSingleNode(
                ASTBinaryNode(
                    ASTBinaryNode(
                        ASTSingleNode(
                            null, PrintScriptToken(TokenType.NUMBER, "1", Coordinate(2, 3), Coordinate(2, 3))
                        ),
                        ASTSingleNode(
                            null, PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 3), Coordinate(2, 3))
                        ),
                        PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    ASTSingleNode(
                        null, PrintScriptToken(TokenType.NUMBER, "3", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    PrintScriptToken(TokenType.STAR, "*", Coordinate(2, 3), Coordinate(2, 3))
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2,3), Coordinate(2, 3)
                )
            )

        val result = printSca.analyze(ast)
        assert(result.size == 1)
    }

    @Test
    fun shouldReturnWarningWhenPrintContainsStringOperation(){
        val ast =
            ASTSingleNode(
                ASTBinaryNode(
                    ASTSingleNode(
                        null, PrintScriptToken(TokenType.STRING, "Hello", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    ASTSingleNode(
                        null, PrintScriptToken(TokenType.STRING, "World", Coordinate(2, 3), Coordinate(2, 3))
                    ),
                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3))
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2,3), Coordinate(2, 3)
                )
            )

        val result = printSca.analyze(ast)
        assert(result.size == 1)
    }

    @Test
    fun shouldReturnEmptyWhenVariableNamesAreCamelCase(){
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null, PrintScriptToken(TokenType.VALUE_IDENTIFIER, "camelCase", Coordinate(2, 3), Coordinate(2, 3)
                    )
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2, 3)
                )
            )
        val result = camelCaseSca.analyze(ast)
        assert(result.isEmpty())
    }


    @Test
    fun shouldReturnWarningWhenVariableNamesAreNotCamelCase(){
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null, PrintScriptToken(TokenType.VALUE_IDENTIFIER, "not_camel_case", Coordinate(2, 3), Coordinate(2, 3)
                    )
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2, 3)
                )
            )
        val result = camelCaseSca.analyze(ast)
        assert(result.size == 1)
    }

    @Test
    fun shouldReturnEmptyWhenVariableNamesAreSnakeCase(){
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null, PrintScriptToken(TokenType.VALUE_IDENTIFIER, "snake_case", Coordinate(2, 3), Coordinate(2, 3)
                    )
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2, 3)
                )
            )
        val result = snakeCaseSca.analyze(ast)
        assert(result.isEmpty())
    }

    @Test
    fun shouldReturnWarningWhenVariableNamesAreNotSnakeCase(){
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null, PrintScriptToken(TokenType.VALUE_IDENTIFIER, "notSnakeCase", Coordinate(2, 3), Coordinate(2, 3)
                    )
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2, 3)
                )
            )
        val result = snakeCaseSca.analyze(ast)
        assert(result.size == 1)
    }

    @Test
    fun variableNameRulesShouldNotAffectPrintStatements(){
        val ast =
            ASTSingleNode(
                ASTBinaryNode(
                    ASTSingleNode(
                        null, PrintScriptToken(TokenType.NUMBER, "1", Coordinate(2, 3), Coordinate(2, 3)
                        )
                    ),
                    ASTSingleNode(
                        null, PrintScriptToken(TokenType.NUMBER, "2", Coordinate(2, 3), Coordinate(2, 3)
                        )
                    ),
                    PrintScriptToken(TokenType.PLUS, "+", Coordinate(2, 3), Coordinate(2, 3)
                    )
                ),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2,3), Coordinate(2, 3)
                )
            )
        val result = camelCaseSca.analyze(ast)
        val result2 = snakeCaseSca.analyze(ast)
        assert(result.isEmpty())
        assert(result2.isEmpty())
    }

    @Test
    fun printRulesShouldNotAffectVariableNames(){
        val ast =
            ASTSingleNode(
                ASTSingleNode(
                    null, PrintScriptToken(TokenType.VALUE_IDENTIFIER, "not_camel_case", Coordinate(2, 3), Coordinate(2, 3)
                    )
                ),
                PrintScriptToken(TokenType.VARIABLE_KEYWORD, "let", Coordinate(2,3), Coordinate(2, 3)
                )
            )
        val result = printSca.analyze(ast)
        assert(result.isEmpty())
    }


}