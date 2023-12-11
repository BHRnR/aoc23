import java.io.File
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val TIMES_EXPANDED = 2

fun eleventh(): Long {
    val image = File("src/main/resources/input11").readLines().map { it.toList() }
    val positions = getGalaxyPositions(image)

    var sum: Long = 0
    for (current in positions.indices)
        for (rest in current + 1..<positions.size)
            sum += getDistance(
                positions[current],
                positions[rest],
                getLinesToExpand(image),
                getColsToExpand(image)
            )

    return sum
}

private fun getLinesToExpand(image: List<List<Char>>): List<Int> {
    val result = mutableListOf<Int>()
    for (i in image.indices)
        if (image[i].all { it == '.' })
            result.add(i)
    return result
}

private fun getColsToExpand(image: List<List<Char>>): List<Int> {
    val transposed = transposeData(image)
    val result = mutableListOf<Int>()
    for (i in transposed.indices)
        if (transposed[i].all { it == '.' })
            result.add(i)
    return result
}

private fun transposeData(image: List<List<Char>>): List<List<Char>> =
    List(image[0].size) { i ->
        List(image.size) { j ->
            image.getOrElse(j) { emptyList() }.getOrElse(i) { '.' }
        }
    }

private fun getGalaxyPositions(image: List<List<Char>>): List<Pair<Int, Int>> =
    image.flatMapIndexed { i, row ->
        row.mapIndexedNotNull { j, char ->
            if (char == '#') i to j else null
        }
    }

private fun getDistance(
    first: Pair<Int, Int>,
    second: Pair<Int, Int>,
    linesToExpand: List<Int>,
    colsToExpand: List<Int>
): Int {
    var linesExpanded = 0
    var colsExpanded = 0

    for (line in min(first.first, second.first)..max(first.first, second.first))
        if (line in linesToExpand) linesExpanded++

    for (col in min(first.second, second.second)..max(first.second, second.second))
        if (col in colsToExpand) colsExpanded++

    return abs(second.first - first.first) +
            abs(second.second - first.second) +
            (linesExpanded + colsExpanded) * (TIMES_EXPANDED - 1)
}
