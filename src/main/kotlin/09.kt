import java.io.File

fun ninth(): Int =
    File("src/main/resources/input9").readLines()
        .map {
            it.split(" ").map { entry -> entry.toInt() }
        }.sumOf { getNextNumber(it) }


private fun getNextNumber(list: List<Int>): Int {
    val allSequences = mutableListOf(list)
    var currentSequence = list
    while (!currentSequence.all { it == 0 }) {
        val nextSequence = currentSequence.zipWithNext { a, b -> b - a }
        allSequences.add(nextSequence)
        currentSequence = nextSequence
    }

    return allSequences.sumOf { it.last() }
}

private fun getNextSequence(list: List<Int>): List<Int> =
    list.zipWithNext { a, b -> b - a }