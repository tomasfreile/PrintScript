package org.example.token

class Coordinate(val row: Int, val column: Int) {
    fun string(): String {
        return "($row, $column)"
    }
}