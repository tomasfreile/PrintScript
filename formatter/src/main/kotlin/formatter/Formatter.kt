package formatter

import ast.AstNode

interface Formatter {
    fun format(astNode: AstNode): String
}
