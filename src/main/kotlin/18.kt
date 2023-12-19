import utils.Direction
import java.io.File

fun eighteenth(): Int {
    val instructions = File("src/main/resources/input18").readLines().map { parseEntry(it) }
    val vertices = mutableListOf(Pair(0, 0))

    for (instruction in instructions) {
        val lastVertex = vertices.last()
        val distanceToNext = getDistanceToNextVertex(instruction)
        vertices.add(Pair(lastVertex.first + distanceToNext.first, lastVertex.second + distanceToNext.second))
    }

    return gausTrapez(vertices) + instructions.sumOf { it.second } / 2 + 1 // Area plus edges
}

private fun gausTrapez(vertizes: List<Pair<Int, Int>>): Int =
    (1..<vertizes.size - 1).sumOf { vertizes[it].first * (vertizes[it - 1].second - vertizes[it + 1].second) } / 2

private fun getDistanceToNextVertex(instruction: Pair<Direction, Int>): Pair<Int, Int> =
    when (instruction.first) {
        Direction.North -> Pair(-instruction.second, 0)
        Direction.East -> Pair(0, instruction.second)
        Direction.South -> Pair(instruction.second, 0)
        Direction.West -> Pair(0, -instruction.second)
    }

private fun parseEntry(line: String): Pair<Direction, Int> {
    val splitString = line.split(" ")
    val direction = when (splitString[0]) {
        "U" -> Direction.North
        "R" -> Direction.East
        "D" -> Direction.South
        "L" -> Direction.West
        else -> error("No valid Direction: ${splitString[0]}")
    }
    return Pair(direction, splitString[1].toInt())
}
