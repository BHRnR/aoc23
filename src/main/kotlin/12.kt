import java.io.File

fun twelfth(): Long {
    checkLine(parseInput()[4])
    return 0
}

private fun checkLine(entry: Pair<List<String>, List<Int>>) {
    groupFitsInBlock(entry.first[0],entry.second[0] )
    return
}

private fun groupFitsInBlock(mapBlock: String, length: Int): Boolean {
    println(mapBlock.length)
    return false
}


private fun parseInput() =
    File("src/main/resources/input12").readLines()
        .map { it.split(" ") }
        .map { Pair(it[0].split("."), it[1].split(",").map { num -> num.toInt() }) }
