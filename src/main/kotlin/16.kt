import utils.Direction
import java.io.File

const val ERROR = "No valid Symbol"

fun sixteenth(): Int {
    val input = File("src/main/resources/input16").readLines().map { it.toList() }
    val isEnergized: MutableList<MutableList<Boolean>> = MutableList(input.size) { MutableList(input[0].size) { false } }
    var currentPositions = setOf(Pair(Pair(0, 0), Direction.West))
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
