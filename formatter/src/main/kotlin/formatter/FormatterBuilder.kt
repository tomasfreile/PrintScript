package formatter

interface FormatterBuilder {
    fun build(
        version: String,
        rulesPath: String,
    ): Formatter
}
