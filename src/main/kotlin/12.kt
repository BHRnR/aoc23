import java.io.File

fun twelfth(): Long {
    parseInput().onEach { line -> run { println("${line.first.map { it.length }}, ${line.second}") } }
    return 0
}

private fun parseInput() =
    File("src/main/resources/input12").readLines()
        .map { it.split(" ") }
        .map { Pair(it[0].split("."), it[1].split(",")) }
