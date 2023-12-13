import java.io.File
import kotlin.math.min

fun thirteenth(): Int {
    return parseInput().sumOf { getMapValue(it) }
}

private fun getMapValue(map: List<String>): Int {
    val transposed = transposeData(map)

    val horizontalSum = (0..<map.size - 1)
        .sumOf { line -> if (isHorizontalMirror(map, line)) (line + 1) * 100 else 0 }

    val verticalSum = (0..<transposed.size - 1)
        .sumOf { line -> if (isHorizontalMirror(transposed, line)) (line + 1) else 0 }

    return horizontalSum + verticalSum
}

private fun isHorizontalMirror(map: List<String>, position: Int) =
    (0..min(position, map.size - 2 - position))
        .all { distance -> map[position - distance] == map[position + 1 + distance] }


private fun parseInput(): List<List<String>> {
    val input = File("src/main/resources/input13").readLines()
    val maps: MutableList<List<String>> = mutableListOf()

    var map: MutableList<String> = mutableListOf()
    for (line in input)
        if (line.isNotEmpty())
            map.add(line)
        else {
            maps.add(map)
            map = mutableListOf()
        }
    maps.add(map)
    return maps
}

private fun transposeData(map: List<String>) =
    map[0].indices.map { col -> map.map { it[col] }.joinToString("") }



