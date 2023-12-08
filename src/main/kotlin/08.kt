import java.io.File

fun eighth(): Int {
    val (directions, nodesMap) = parseInput()

    var position = "AAA"
    var currentStep = 0

    while (position != "ZZZ") {
        position = if (directions[currentStep % directions.length] == 'L')
            nodesMap[position]?.get(0).toString()
        else nodesMap[position]?.get(1).toString()
        currentStep++
    }

    return currentStep
}

private fun parseInput(): Pair<String,Map<String,List<String>>> {
    val input = File("src/main/resources/input8").readLines()
    val directions = input[0]
    input.removeFirst()
    input.removeFirst()

    val nodesMap = input.associate {
        val splitString = it.split(" = ")
        val positon = splitString[0]
        val nodes = splitString[1].replace("(", "").replace(")", "").split(", ")
        positon to listOf(nodes[0], nodes[1])
    }.toMap()
        .onEach { println(it) }
    return Pair(directions,nodesMap)
}

