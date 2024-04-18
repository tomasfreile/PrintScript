package formatter.formatOperations.commons

class HandleSpace {
    // maneja que no haya espacios duplicados y que los espacios se asignen correctamente segun las reglas
    fun handleSpaces(
        tokenValue: String,
        spaceBefore: Boolean,
        spaceAfter: Boolean,
        result: String,
    ): String {
        val formattedBefore = handleBeforeSpace(tokenValue, spaceBefore, result)
        return handleAfterSpace(formattedBefore, spaceAfter)
    }

    // maneja que los espacios anterior al token sean correctos
    private fun handleBeforeSpace(
        tokenValue: String,
        spaceBefore: Boolean,
        result: String,
    ): String {
        return if (spaceBefore && result.endsWith(" ")) {
            result + tokenValue
        } else if (spaceBefore && !result.endsWith(" ")) {
            "$result $tokenValue"
        } else if (!spaceBefore && result.endsWith(" ")) {
            val modifiedResult = result.dropLast(1)
            modifiedResult + tokenValue
        } else {
            result + tokenValue
        }
    }

    // maneja que los espacios despues del token sean correctos
    private fun handleAfterSpace(
        result: String,
        spaceAfter: Boolean,
    ): String {
        return if (spaceAfter) {
            "$result "
        } else {
            result
        }
    }
}
