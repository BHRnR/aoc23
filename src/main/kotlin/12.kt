import java.io.File

fun twelfth(): Long {
    return File("src/main/resources/input12").readLines()
        .map { it.split(" ") }
//        .map { parseA(it) } // Part 1
        .map { parseB(it) } // Part 2
        .onEach { println(it.second) }
        .sumOf { count(it.first, it.second) }
}

private fun parseA(input: List<String>) =
    Pair(input[0], input[1].split(",").map { num -> num.toInt() })


private fun parseB(input: List<String>): Pair<String, List<Int>> =
    Pair(
        "${input[0]}?".repeat(5).dropLast(1),
        "${input[1]},".repeat(5).dropLast(1).split(",").map { it.toInt() }
    )


private val cache = hashMapOf<Pair<String, List<Int>>, Long>()
private fun count(config: String, groups: List<Int>): Long {
    if (groups.isEmpty()) return if ("#" in config) 0 else 1
    if (config.isEmpty()) return 0

    return cache.getOrPut(Pair(config, groups)) {
        var result = 0L
        if (config[0] in listOf('.', '?'))
            result += count(config.drop(1), groups)
        if (config[0] in listOf('#', '?')
            && groups[0] <= config.length && "." !in config.take(groups[0])
            && (groups[0] == config.length || config[groups[0]] != '#')
        )
            result += count(config.drop(groups[0] + 1), groups.drop(1))
        result
    }
}