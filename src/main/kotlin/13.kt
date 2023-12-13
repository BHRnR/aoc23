import java.io.File
import java.lang.IllegalStateException
import kotlin.math.min

enum class Alignment { HORIZONTAL, VERTICAL }

fun thirteenth() = parseInput()
//        .map { getMirrorPosition(it) } //Part 1
    .map { getAlternativeWithFlipped(it) } //Part 2
    .sumOf { getSum(it) }


private fun getMirrorPosition(map: List<String>): Pair<Int, Alignment> {
    val positions = getMirrorPositions(map)
    check(positions.size == 1)
    return positions[0]
}

private fun getMirrorPositions(map: List<String>): List<Pair<Int, Alignment>> {
    val result = mutableListOf<Pair<Int, Alignment>>()
    val transposed = transposeData(map)

    for (line in 0..map.size - 2)
        if (isHorizontalMirror(map, line))
            result.add(Pair(line, Alignment.HORIZONTAL))

    for (col in 0..transposed.size - 2)
        if (isHorizontalMirror(transposed, col))
            result.add(Pair(col, Alignment.VERTICAL))

    return result
}

private fun getAlternativeWithFlipped(map: List<String>): Pair<Int, Alignment> {
    val oldPosition = getMirrorPosition(map)
    for (line in map.indices)
        for (col in map[line].indices) {
            val mapWithFlipped = getFlippedAt(map, line, col)
            val newPositions = getMirrorPositions(mapWithFlipped).toMutableList()

            if (newPositions.any { it != oldPosition })
                return newPositions.first { it != oldPosition }
        }
    throw IllegalStateException("Now new mirror position found")
}

private fun getFlippedAt(map: List<String>, line: Int, col: Int): List<String> {
    val result = map.map { it.toMutableList() }
    result[line][col] = if (map[line][col] == '#') '.' else '#'
    return result.map { String(it.toCharArray()) }
}

private fun isHorizontalMirror(map: List<String>, position: Int) =
    (0..min(position, map.size - 2 - position))
        .all { distance -> map[position - distance] == map[position + 1 + distance] }


private fun parseInput(): List<List<String>> {
    val input = File("src/main/resources/input13").readLines()
    val maps: MutableList<List<String>> = mutableListOf()

    var map: MutableList<String> = mutableListOf()
    for (line in input)
        if (line.isNotEmpty()) {
            map.add(line)
        } else {
            maps.add(map)
            map = mutableListOf()
        }
    maps.add(map)
    return maps
}

private fun transposeData(map: List<String>) =
    map[0].indices.map { col -> map.map { it[col] }.joinToString("") }

private fun getSum(position: Pair<Int, Alignment>) =
    if (position.second == Alignment.HORIZONTAL)
        ((position.first + 1) * 100)
    else position.first + 1
