import java.io.File

fun fifteenth(): Int = File("src/main/resources/input15").readText().split(",")
    .map { it.trim() }.sumOf { getHashForLine(it) }

private fun getHashForLine(line: String): Int =
    line.fold(0) { acc, c -> getHashForChar(acc, c) }

private fun getHashForChar(oldValue: Int, char: Char): Int =
    ((oldValue + char.code) * 17) % 256
