@file:Suppress("ktlint:standard:no-wildcard-imports")

package parser.ifParser

import ast.*
import org.junit.jupiter.api.Test
import parser.parserBuilder.printScript11.IfParserBuilder
import position.Coordinate
import token.PrintScriptToken
import token.TokenType
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class IfPrintScriptParserTest {
    private val parser = IfParserBuilder().build()

    @Test
    fun test001_IfParserCanHandle() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            parser.canHandle(tokenList)
        }
    }

    @Test
    fun test002_IfParserWithInvalidBraceInThenBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            parser.canHandle(tokenList)
        }
        assertFails {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test003_IfParserWithInvalidBraceInElseBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            parser.canHandle(tokenList)
        }
        assertFails {
            parser.createAST(tokenList)
        }
    }

    @Test
    fun test004_IfParserCanHandleDeclarationInBothBlocks() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Jorge", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue {
            parser.canHandle(tokenList)
        }
    }

    @Test
    fun test005_IfParserDeclarationInBothBlocksNode() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Tatiana", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Jorge", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val node = parser.createAST(tokenList)
        assertTrue {
            node is IfNode
        }
        node as IfNode
        val thenNodes = (node.thenBlock as CodeBlock).nodes
        val elseNodes = (node.elseBlock as CodeBlock).nodes
        assertTrue {
            thenNodes.first() is VariableDeclarationNode
            elseNodes.first() is VariableDeclarationNode
        }
    }

    @Test
    fun test006_IfParserIfConditionThenDeclareNumberAndPrintElseAssignationString() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERTYPE, "Int", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.NUMBERLITERAL, "3", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "three", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Jorge", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val node = parser.createAST(tokenList)
        assertTrue {
            node is IfNode
        }
        node as IfNode
        val thenNodes = (node.thenBlock as CodeBlock).nodes
        val elseNodes = (node.elseBlock as CodeBlock).nodes
        assertEquals(thenNodes.size, 2)
        assertTrue {
            elseNodes.first() is AssignmentNode
        }
        val declaration = thenNodes[0]
        val printDeclaration = thenNodes[1]
        assertTrue {
            declaration is VariableDeclarationNode
            printDeclaration is PrintNode
        }
    }

    @Test
    fun test007_IfParserNestedIfInThenBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Jorge", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.CONST, "const", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "anotherCondition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANTYPE, "Boolean", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val node = parser.createAST(tokenList)
        node as IfNode
        val thenNodes = (node.thenBlock as CodeBlock).nodes
        val elseNodes = (node.elseBlock as CodeBlock).nodes
        assertEquals(thenNodes.size, 3)
        assertEquals(elseNodes.size, 1)
        // then block
        val declarationNode = thenNodes[0]
        val ifNode = thenNodes[1]
        val printNode = thenNodes[2]
        assertTrue {
            declarationNode is VariableDeclarationNode
            ifNode is IfNode
            printNode is PrintNode
        }
        // else block
        val booleanDeclaration = elseNodes[0]
        assertTrue {
            booleanDeclaration is VariableDeclarationNode
        }
    }

    @Test
    fun test008_IfParserNestedIfInElseBlock() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LET, "let", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGTYPE, "String", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Marcos", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.PRINT, "println", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.CONST, "const", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "anotherCondition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.COLON, ":", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANTYPE, "Boolean", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Jorge", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        assertTrue { parser.canHandle(tokenList) }
        val node = parser.createAST(tokenList)
        node as IfNode
        val thenNodes = (node.thenBlock as CodeBlock).nodes
        val elseNodes = (node.elseBlock as CodeBlock).nodes
        assertEquals(thenNodes.size, 2)
        assertEquals(elseNodes.size, 2)
        // then block
        val declarationNode = thenNodes[0]
        val printNode = thenNodes[1]
        assertTrue {
            declarationNode is VariableDeclarationNode
            printNode is PrintNode
        }
        // else block
        val booleanDeclaration = elseNodes[0]
        val ifNode = elseNodes[1]
        assertTrue {
            booleanDeclaration is VariableDeclarationNode
            ifNode is IfNode
        }
        ifNode as IfNode
        val thenNestedNodes = (ifNode.thenBlock as CodeBlock).nodes
        assertEquals(thenNestedNodes.size, 1)
    }

    @Test
    fun test009_ParserNestedIfInBothBlocks() {
        val tokenList =
            listOf(
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.BOOLEANLITERAL, "true", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Jorge", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ELSE, "else", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.IF, "if", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTPAREN, "(", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "condition", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTPAREN, ")", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.LEFTBRACE, "{", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.VALUEIDENTIFIERLITERAL, "name", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.ASSIGNATION, "=", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.STRINGLITERAL, "Jorge", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.SEMICOLON, ";", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
                PrintScriptToken(TokenType.RIGHTBRACE, "}", Coordinate(2, 3), Coordinate(2, 3)),
            )
        val node = parser.createAST(tokenList)
        node as IfNode
        val thenBlockNodes = (node.thenBlock as CodeBlock).nodes
        val elseBlockNodes = (node.elseBlock as CodeBlock).nodes
        assertEquals(thenBlockNodes.size, 1)
        assertEquals(elseBlockNodes.size, 1)
        val thenContent = thenBlockNodes[0]
        val elseContent = elseBlockNodes[0]
        assertTrue {
            thenContent is IfNode
            elseContent is IfNode
        }
    }
}
