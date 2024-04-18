package formatter.formatOperations.commons

class FileDataChecker {
    // chequea que el dato del mapa este y sea de tipo booleano
    fun checkBooleanData(
        data: Map<String, Any>,
        key: String,
    ): Boolean {
        val value =
            data[key] as? Boolean
                ?: throw NullPointerException("invalid value for '$key', check that the config file is correct")
        return value
    }

    // chequea que el dato del mapa este y sea de tipo int
    fun checkNumberData(
        data: Map<String, Any>,
        key: String,
    ): Int {
        val value =
            data[key] as? Int
                ?: throw NullPointerException("invalid value for '$key', check that the config file is correct")
        return value
    }
}
