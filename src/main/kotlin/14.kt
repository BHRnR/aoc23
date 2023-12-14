import java.io.File
import java.util.*


fun fourteenth(): Int {
    val data = File("src/main/resources/input14").readLines()
    return rotateDataLeft(data)
        .map { moveToLeft(it) }
        .sumOf { getWeightFromRight(it) }
}

private fun moveToLeft(original: String): String {
    val result = original.toMutableList()
    for (i in result.indices)
        if (result[i] == 'O')
            Collections.swap(result, i, findNextFixed(result, i) + 1)

    return result.toCharArray().concatToString()
}

private fun findNextFixed(line: List<Char>, position: Int): Int =
    (position - 1 downTo 0).firstOrNull { line[it] != '.' } ?: -1

private fun getWeightFromRight(line: String): Int =
    line.reversed()
        .mapIndexed { index, char -> if (char == 'O') index + 1 else 0 }
        .sum()

private fun rotateDataRight(map: List<String>): List<String> {
    val result = MutableList(map[0].length) { "" }
    for (i in map.size - 1 downTo 0)
        for (j in map[0].indices)
            result[j] = result[j] + map[i][j]
    return result
}

private fun rotateDataLeft(map: List<String>): List<String> {
    val result = MutableList(map[0].length) { "" }
    for (i in map.indices)
        for (j in map[0].length - 1 downTo 0)
            result[j] = result[j] + map[i][j]
    return result
}
