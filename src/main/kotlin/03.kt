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
    val inputLines = getData()
    var sum = 0
    for (row in inputLines.indices)
        for (column in inputLines[0].indices)
            if (inputLines[row][column] == '*') {
                val neighbours = getNeighbouringNumbers(inputLines, row, column)
                if (neighbours.size == 2)
                    sum += neighbours[0] * neighbours[1]
            }
    return sum
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
    return getHorizontalNumbers(data, row, column) + getVerticalNumbers(data, row, column)
}

private fun getVerticalNumbers(data: List<String>, row: Int, column: Int): List<Int> {
    val result = mutableListOf<Int>()
    for (currentRow in arrayOf(row - 1, row + 1)) {
        val digitsToCheck = (-1..1).map { data[currentRow][column + it] }.toList()
        var numberLeft = ""
        var numberRight = ""

        if (digitsToCheck[0].isDigit())
            numberLeft = getNumber(data, currentRow, column, -1)
        if (digitsToCheck[2].isDigit())
            numberRight = getNumber(data, currentRow, column, 1)

        if (digitsToCheck[1].isDigit()) {
            result.add((numberLeft + digitsToCheck[1] + numberRight).toInt())
        } else {
            if (numberLeft != "")
                result.add(numberLeft.toInt())
            if (numberRight != "")
                result.add(numberRight.toInt())
        }
    }
    return result
}

private fun getHorizontalNumbers(data: List<String>, row: Int, column: Int): List<Int> {
    val numbers = mutableListOf<Int>()
    for (orientation in intArrayOf(1, -1))
        if (data[row][column + orientation].isDigit())
            numbers.add(getNumber(data, row, column, orientation).toInt())
    return numbers
}

private fun getNumber(data: List<String>, row: Int, column: Int, direction: Int): String {
    var number = ""
    var i = 1

    while (data[row][column + i * direction].isDigit()) {
        number += data[row][column + i * direction]
        i++
    }

    return if (direction == 1) number else number.reversed()
}
