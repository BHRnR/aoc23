import java.io.File

fun thirdA(): Int {
    val inputLines = getData()
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

fun thirdB(): Int {
    //TODO: Not finished yet
    val inputLines = getData()
    var sum = 0
    for (row in inputLines.indices) {
        for (column in inputLines[0].indices) {
            if (inputLines[row][column] == '*')
                println(getNeighbouringNumbers(inputLines, row, column))
        }
    }
    return 0
}

private fun getData(): List<String> {
    val input = File("src/main/resources/input3").readLines()
// Add dots around to take care of index out of bounds issues
    return mutableListOf(".".repeat(input[0].length + 2)) +
            input.map { line -> ".$line." } +
            listOf(".".repeat(input[0].length + 2))
}

private fun hasSymbolNeighbour(data: List<String>, row: Int, column: Int, length: Int): Boolean {
    for (r in row - 1..row + 1)
        for (c in column - 1 - length..column)
            if (isSymbol(data[r][c]))
                return true
    return false
}

private fun isSymbol(char: Char): Boolean = char != '.' && !char.isDigit()

private fun getNeighbouringNumbers(data: List<String>, row: Int, column: Int): List<Int> {
    val numbers = mutableListOf<Int>()
    for (r in row - 1..row + 1)
        for (c in column - 1..column + 1)
            if (data[r][c].isDigit())
                numbers.add(data[r][c].code)
    return numbers
}
