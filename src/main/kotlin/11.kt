import java.io.File
import kotlin.math.abs

fun eleventh(): Int {
    val input = File("src/main/resources/input11").readLines().map { it.toList() }

    val duplicatedLines = duplicateEmptyLines(input)
    val duplicatedColumns = transposeData(duplicateEmptyLines(transposeData(duplicatedLines)))
    val positions = getGalaxyPositions(duplicatedColumns)

    var sum = 0
    positions.indices.forEach { current ->
        (current..<positions.size).forEach { rest ->
            sum += getDistance(positions[current], positions[rest])
        }
    }

    return sum
}

private fun duplicateEmptyLines(data: List<List<Char>>): List<List<Char>> {
    val extendedData = data.toMutableList()
    for (i in data.indices.reversed())
        if (data[i].all { it == '.' })
            extendedData.add(i, List(data[i].size) { '.' })
    return extendedData.toList()
}

private fun transposeData(data: List<List<Char>>): List<List<Char>> =
    List(data[0].size) { i ->
        List(data.size) { j ->
            data.getOrElse(j) { emptyList() }.getOrElse(i) { '.' }
        }
    }

private fun getGalaxyPositions(data: List<List<Char>>): List<Pair<Int, Int>> =
    data.flatMapIndexed { i, row ->
        row.mapIndexedNotNull { j, char ->
            if (char == '#') i to j else null
        }
    }

private fun getDistance(first: Pair<Int, Int>, second: Pair<Int, Int>): Int =
    abs(second.first - first.first) + abs(second.second - first.second)

