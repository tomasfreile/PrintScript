package formatter.formatOperations.commons

class FormatSemicolon {
    // pone el punto y coma, revisando por las dudas si hay un espacio que sobra antes de ponerlo,
    // en caso de que haya lo saca al espacio y pone el punto y coma
    fun formatSemicolon(text: String): String {
        if (text.endsWith(" ")) {
            val modifiedText = text.dropLast(1)
            return "$modifiedText;\n"
        } else {
            return "$text;\n"
        }
    }
}
