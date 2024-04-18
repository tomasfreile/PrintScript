package formatter.formatOperations.commons

class HandleSpace {
    fun handleSpaces(
        tokenValue: String,
        spaceBefore: Boolean,
        spaceAfter: Boolean,
        result: String,
    ): String {
        val formattedBefore = handleBeforeSpace(tokenValue, spaceBefore, result)
        return handleAfterSpace(formattedBefore, spaceAfter)
    }

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
