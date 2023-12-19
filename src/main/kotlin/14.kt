import java.io.File
import java.util.*

const val TOTAL_ROTATIONS = 1000000000

// Note: Directions are shifted clockwise (North is to the right, East down, ...)
fun fourteenth(): Int {
    val initial = rotateDataRight(File("src/main/resources/input14").readLines())

    val pastResults = mutableMapOf<Int, List<String>>()

    var current = initial
    for (i in 1..TOTAL_ROTATIONS) {
        current = rotateAndShiftCompletely(current)
        if (current in pastResults.values) {
            val lastOccurrence = pastResults.filterValues { it == current }.keys.first()
            val loopLength = i - lastOccurrence
            val modulus = TOTAL_ROTATIONS % loopLength
            current =
                if (TOTAL_ROTATIONS % loopLength in lastOccurrence..i) pastResults[modulus]!!
                else pastResults[modulus + loopLength]!!
            break
        } else {
            pastResults[i] = current
        }
    }

//    return getWeight(data.map { moveToRight(it) }) // Part 1
    return getWeight(current) // Part 2
}

private fun rotateAndShiftCompletely(data: List<String>): List<String> =
    (1..4).fold(data) { acc, _ -> rotateDataRight(acc.map { moveToRight(it) }) }

private fun moveToRight(original: String): String {
    val result = original.toMutableList()
    for (i in result.indices.reversed())
        if (result[i] == 'O')
            Collections.swap(result, i, findNextFixed(result, i) - 1)

    return result.toCharArray().concatToString()
}

private fun findNextFixed(line: List<Char>, position: Int): Int =
    (position + 1 until line.size).firstOrNull { line[it] != '.' } ?: line.size

private fun getWeight(data: List<String>): Int =
    data.sumOf { getWeightFromLeft(it) }

private fun getWeightFromLeft(line: String): Int =
    line.mapIndexed { index, char -> if (char == 'O') index + 1 else 0 }.sum()

private fun rotateDataRight(data: List<String>): List<String> {
    val result = MutableList(data[0].length) { "" }
    for (i in data.size - 1 downTo 0)
        for (j in data[0].indices)
            result[j] = result[j] + data[i][j]
    return result
}
