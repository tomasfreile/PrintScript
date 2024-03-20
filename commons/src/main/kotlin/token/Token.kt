package token

interface Token {
    val type : TokenType
    val value : String
    val start: Coordinate
    val end: Coordinate

    fun equals(other: Token): Boolean {
        return this.type == other.type && this.value == other.value &&
                this.start.row == other.start.row && this.start.column == other.start.column &&
                this.end.row == other.end.row && this.end.column == other.end.column
    }

    fun string(): String {
        return "Type: ${type.name}, Value: $value, Start: ${start.string()}, End: ${end.string()}"
    }
}