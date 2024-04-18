package formatter.formatOperations.commons

class FormatSemicolon {
    fun formatSemicolon(text: String): String {
        if (text.endsWith(" ")) {
            val modifiedText = text.dropLast(1)
            return "$modifiedText;\n"
        } else {
            return "$text;\n"
        }
    }
}
