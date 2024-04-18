package formatter

import ast.AstNode

interface Formatter {
    fun format(astNode: AstNode): String

    fun getRules(): Map<String, Any>
}
