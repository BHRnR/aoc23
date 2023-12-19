import java.io.File

data class Part(
    val x: Int,
    val m: Int,
    val a: Int,
    val s: Int
) {
    override fun toString(): String {
        return "Part(x=$x, m=$m, a=$a, s=$s)"
    }
}

data class Criterion(
    val category: Char? = null,
    val greater: Boolean? = null,
    val value: Int? = null,
    val destination: String
) {
    override fun toString(): String {
        return "Criterion(type=$category, greater=$greater, value=$value, destination='$destination')"
    }
}

fun nineteenth(): Int {
    val input = parseInput()
    val instructions = input.first
    val parts = input.second

    val accepted = mutableListOf<Part>()

    parts.map {
        var currentLabel = "in"
        while (currentLabel !in listOf("A", "R")) {
            val currentInstruction = instructions[currentLabel]!!
            for (criterion in currentInstruction) {
                if (checkCriterion(it, criterion)) {
                    currentLabel = criterion.destination
                    break
                }
            }
        }
        if (currentLabel == "A")
            accepted.add(it)
    }

    return accepted.sumOf { it.x + it.m + it.a + it.s }
}

private fun checkCriterion(part: Part, criterion: Criterion): Boolean {
    if (criterion.greater == null || criterion.value == null || criterion.category == null)
        return true

    val value = when (criterion.category) {
        'x' -> part.x
        'm' -> part.m
        'a' -> part.a
        's' -> part.s
        else -> error("Invalid category: ${criterion.category}")
    }

    return if (criterion.greater) value > criterion.value else value < criterion.value
}

private fun parseInput(): Pair<Map<String, List<Criterion>>, List<Part>> {
    val input = File("src/main/resources/input19").readLines()
    val emptyLine = input.indexOf("")
    return Pair(
        input.take(emptyLine).map { parseInstruction(it) }.toMap(),
        input.subList(emptyLine + 1, input.size).map { parsePart(it) })
}

private fun parseInstruction(input: String): Pair<String, List<Criterion>> {
    val splitString = input.replace("}", "").split("{")
    val criteria = splitString[1].split(",").map { parseCriterion(it) }
    return Pair(splitString[0], criteria)
}

private fun parseCriterion(input: String): Criterion {
    if (!input.contains(":"))
        return Criterion(destination = input)

    val type = input[0]
    val greater = input.contains(">")
    val value = input.filter { it.isDigit() }.toInt()
    val destination = input.split(":")[1]
    return Criterion(type, greater, value, destination)
}

private fun parsePart(input: String): Part {
    val values = input.replace("{", "").replace("}", "").split(",")
        .map { it.drop(2).toInt() }
    return Part(values[0], values[1], values[2], values[3])
}
