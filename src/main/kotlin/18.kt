import utils.Direction
import java.io.File

fun eighteenth(): Long {
    val instructions = File("src/main/resources/input18").readLines()
//        .map { parseEntryA(it) } // Part A
        .map { parseEntryB(it) }
    val vertices = mutableListOf(Pair(0L, 0L))

    for (instruction in instructions) {
        val lastVertex = vertices.last()
        val distanceToNext = getDistanceToNextVertex(instruction)
        vertices.add(Pair(lastVertex.first + distanceToNext.first, lastVertex.second + distanceToNext.second))
    }

    return gausTrapez(vertices) + instructions.sumOf { it.second } / 2 + 1 // Area plus edges
}

private fun gausTrapez(vertizes: List<Pair<Long, Long>>): Long =
    (1..<vertizes.size - 1).sumOf { vertizes[it].first * (vertizes[it - 1].second - vertizes[it + 1].second) } / 2L

private fun getDistanceToNextVertex(instruction: Pair<Direction, Long>): Pair<Long, Long> =
    when (instruction.first) {
        Direction.North -> Pair(-instruction.second, 0)
        Direction.East -> Pair(0, instruction.second)
        Direction.South -> Pair(instruction.second, 0)
        Direction.West -> Pair(0, -instruction.second)
    }

private fun parseEntryA(line: String): Pair<Direction, Long> {
    val splitString = line.split(" ")
    val direction = when (splitString[0]) {
        "U" -> Direction.North
        "R" -> Direction.East
        "D" -> Direction.South
        "L" -> Direction.West
        else -> error("No valid Direction: ${splitString[0]}")
    }
    return Pair(direction, splitString[1].toLong())
}

private fun parseEntryB(line: String): Pair<Direction, Long> {
    val hexString = line.split(" ")[2].replace("(#", "").replace(")", "")
    val distance = hexString.take(5).toLong(16)
    val direction = when (hexString[5]) {
        '0' -> Direction.East
        '1' -> Direction.South
        '2' -> Direction.West
        '3' -> Direction.North
        else -> error("Not mappable to Direction: ${hexString[5]}")
    }
    return Pair(direction, distance)
}
