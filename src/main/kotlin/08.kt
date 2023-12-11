import java.io.File
import kotlin.math.max
import kotlin.math.min

fun eighth(): Long {
    val (directions, nodesMap) = parseInput()
//    return part1(directions, nodesMap)
    return part2(directions, nodesMap)
}

private fun part1(directions: String, nodesMap: Map<String, Pair<String, String>>): Int {
    var position = "AAA"
    var currentStep = 0

    while (position != "ZZZ") {
        position = if (directions[currentStep % directions.length] == 'L')
            nodesMap[position]?.first.toString()
        else nodesMap[position]?.second.toString()
        currentStep++
    }

    return currentStep
}

private fun part2(directions: String, nodesMap: Map<String, Pair<String, String>>): Long {
    val positions = nodesMap.filter { it.key.last() == 'A' }.map { it.key }.toMutableList()
    val steps = MutableList(positions.size) { 0 }

    for (i in positions.indices)
        while (positions[i].last() != 'Z') {
            println(positions[i])
            positions[i] =
                if (directions[steps[i] % directions.length] == 'L')
                    nodesMap[positions[i]]?.first.toString()
                else nodesMap[positions[i]]?.second.toString()
            steps[i]++
        }
    return steps.map { it.toLong() }.fold(1) { acc, next -> findLCM(acc, next) }
}

private fun findLCM(a: Long, b: Long): Long {
    val larger = max(a, b)
    val maxLcm = a * b

    for (lcm in larger..maxLcm step larger)
        if (lcm % min(a, b) == 0L)
            return lcm

    return maxLcm
}

private fun parseInput(): Pair<String, Map<String, Pair<String, String>>> {
    val input = File("src/main/resources/input8").readLines()
    val directions = input[0]
    input.removeFirst()
    input.removeFirst()

    val nodesMap = input.associate {
        val splitString = it.split(" = ")
        val position = splitString[0]
        val nodes = splitString[1].replace("(", "").replace(")", "").split(", ")
        position to Pair(nodes[0], nodes[1])
    }.toMap()

    return Pair(directions, nodesMap)
}

