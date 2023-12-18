import utils.Direction
import java.io.File

const val ERROR = "No valid Symbol"

fun sixteenth(): Int {
    val input = File("src/main/resources/input16").readLines().map { it.toList() }
//    return getEnergizedForStartingPosition(input, Pair(Pair(0, 0), Direction.West)) // Part 1
    return partB(input) // Part 2
}

private fun partB(input: List<List<Char>>): Int {
    var max = 0
    val startingPositions = getStartingPositions(input)
    for ((counter, startingPosition) in startingPositions.withIndex()) {
        println("Checking starting position ${counter + 1} of ${startingPositions.size}, current max = $max")
        val energized = getEnergizedForStartingPosition(input, startingPosition)
        if (energized > max)
            max = energized
    }
    return max
}

private fun getEnergizedForStartingPosition(input: List<List<Char>>, startingPosition: Pair<Pair<Int, Int>, Direction>): Int {
    val isEnergized: MutableList<MutableList<Boolean>> = MutableList(input.size) { MutableList(input[0].size) { false } }
    var currentPositions = setOf(startingPosition)
    val lastPositions = mutableSetOf<Set<Pair<Pair<Int, Int>, Direction>>>()

    while (currentPositions.isNotEmpty() && (currentPositions !in lastPositions)) {
        currentPositions.map { it.first }.forEach { isEnergized[it.first][it.second] = true }
        lastPositions.add(currentPositions)

        currentPositions = currentPositions
            .flatMap { getNextPositions(input, it.first, it.second) }
            .filter { it.first.first in (input.indices) && it.first.second in (input[0].indices) }
            .toSet()
    }

    return isEnergized.flatten().count { it }
}

private fun getStartingPositions(input: List<List<Char>>): Set<Pair<Pair<Int, Int>, Direction>> {
    val startingPositions = mutableSetOf<Pair<Pair<Int, Int>, Direction>>()
    for (line in input.indices) {
        startingPositions.add(Pair(Pair(line, 0), Direction.West))
        startingPositions.add(Pair(Pair(line, input[0].size - 1), Direction.East))
    }
    for (col in input[0].indices) {
        startingPositions.add(Pair(Pair(0, col), Direction.North))
        startingPositions.add(Pair(Pair(input.size - 1, col), Direction.South))
    }
    return startingPositions
}

private fun getNextPositions(
    input: List<List<Char>>,
    currentPosition: Pair<Int, Int>,
    comingFrom: Direction
): Set<Pair<Pair<Int, Int>, Direction>> {
    val currentSymbol = input[currentPosition.first][currentPosition.second]
    return when (comingFrom) {
        Direction.North -> {
            when (currentSymbol) {
                '.', '|' -> setOf(Pair(Pair(currentPosition.first + 1, currentPosition.second), Direction.North))
                '/' -> setOf(Pair(Pair(currentPosition.first, currentPosition.second - 1), Direction.East))
                '\\' -> setOf(Pair(Pair(currentPosition.first, currentPosition.second + 1), Direction.West))
                '-' -> setOf(
                    Pair(Pair(currentPosition.first, currentPosition.second + 1), Direction.West),
                    Pair(Pair(currentPosition.first, currentPosition.second - 1), Direction.East)
                )

                else -> throw IllegalArgumentException(ERROR)
            }
        }

        Direction.East -> {
            when (currentSymbol) {
                '.', '-' -> setOf(Pair(Pair(currentPosition.first, currentPosition.second - 1), Direction.East))
                '/' -> setOf(Pair(Pair(currentPosition.first + 1, currentPosition.second), Direction.North))
                '\\' -> setOf(Pair(Pair(currentPosition.first - 1, currentPosition.second), Direction.South))
                '|' -> setOf(
                    Pair(Pair(currentPosition.first + 1, currentPosition.second), Direction.North),
                    Pair(Pair(currentPosition.first - 1, currentPosition.second), Direction.South)
                )

                else -> throw IllegalArgumentException(ERROR)
            }
        }

        Direction.South -> {
            when (currentSymbol) {
                '.', '|' -> setOf(Pair(Pair(currentPosition.first - 1, currentPosition.second), Direction.South))
                '/' -> setOf(Pair(Pair(currentPosition.first, currentPosition.second + 1), Direction.West))
                '\\' -> setOf(Pair(Pair(currentPosition.first, currentPosition.second - 1), Direction.East))
                '-' -> setOf(
                    Pair(Pair(currentPosition.first, currentPosition.second + 1), Direction.West),
                    Pair(Pair(currentPosition.first, currentPosition.second - 1), Direction.East)
                )

                else -> throw IllegalArgumentException(ERROR)
            }
        }

        Direction.West -> {
            when (currentSymbol) {
                '.', '-' -> setOf(Pair(Pair(currentPosition.first, currentPosition.second + 1), Direction.West))
                '/' -> setOf(Pair(Pair(currentPosition.first - 1, currentPosition.second), Direction.South))
                '\\' -> setOf(Pair(Pair(currentPosition.first + 1, currentPosition.second), Direction.North))
                '|' -> setOf(
                    Pair(Pair(currentPosition.first + 1, currentPosition.second), Direction.North),
                    Pair(Pair(currentPosition.first - 1, currentPosition.second), Direction.South)
                )

                else -> throw IllegalArgumentException(ERROR)
            }
        }
    }
}
