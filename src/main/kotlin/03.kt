import java.io.File
import java.lang.IndexOutOfBoundsException

fun thirdA(): Int {
    val inputLines = File("src/main/resources/input3").readLines()
        .map { line -> "$line." } // Make sure line does not end with digit
    var sum = 0
    for (row in inputLines.indices) {
        var part = ""
        for (column in inputLines[0].indices) {
            if (inputLines[row][column].isDigit()) {
                part += inputLines[row][column]
            } else if (part != "" && hasSymbolNeighbour(inputLines, row, column, part.length)) {
                sum += part.toInt()
                part = ""
            } else {
                part = ""
            }
        }
    }
    return sum
}

private  fun hasSymbolNeighbour(data: List<String>, row: Int, column: Int, length: Int): Boolean {
    for (r in row - 1..row + 1)
        for (c in column - 1 - length..column)
            try {
                if (isSymbol(data[r][c]))
                    return true
            } catch (ignore: IndexOutOfBoundsException) {} // there is never a symbol outside the data
    return false
}

private fun isSymbol(char: Char): Boolean = char != '.' && !char.isDigit()

