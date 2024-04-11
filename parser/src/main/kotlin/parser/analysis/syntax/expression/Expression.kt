package parser.analysis.syntax.expression

import token.Token

interface Expression {
    fun isExpression(expression: List<Token>): Boolean
}
