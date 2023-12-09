import java.io.File

fun ninth(): Int =
    File("src/main/resources/input9").readLines()
        .map { it.split(" ").map { entry -> entry.toInt() } }
        .map { getSequences(it) }
//        .sumOf { getNextNumber(it) } // Part 1
        .sumOf { getPreviousNumber(it) } // Part 2

private fun getSequences(list: List<Int>): List<List<Int>> =
    generateSequence(list) { current -> if (current.all { it == 0 }) null else getNextSequence(current) }.toList()

private fun getNextSequence(list: List<Int>): List<Int> = list.zipWithNext { a, b -> b - a }

fun getNextNumber(sequences: List<List<Int>>): Int = sequences.sumOf { it.last() }

private fun getPreviousNumber(sequences: List<List<Int>>): Int =
    sequences.foldRight(0) { currentSequence, accumulator -> currentSequence.first() - accumulator }