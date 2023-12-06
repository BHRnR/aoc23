import java.io.File

fun sixthA(): Long {
    val input = File("src/main/resources/input6").readLines()
        .map { it.split(":")[1] }
        .map { parseInputA(it) } //Part 1
//        .map { parseInputB(it) } //Part 2

    val time = input[0]
    val record = input[1]

    return time.indices
        .map { getAmountOfPossibilities(time[it], record[it]) }
        .fold(1) { total, next -> total * next }
}

private fun parseInputA(line: String): List<Long> =
    line.split(" ").filter { it.isNotEmpty() }.map { it.toLong() }

private fun parseInputB(line: String): List<Long> =
    listOf(line.replace(" ", "").toLong())


private fun getAmountOfPossibilities(time: Long, record: Long) =
    getPossibleDistances(time).filter { it > record }.size

private fun getPossibleDistances(time: Long): List<Long> =
    (0..time).map { (time - it) * it }

