import java.io.File
import java.lang.IllegalArgumentException
import kotlin.math.abs

private val input = getData(File("src/main/resources/input10").readLines())

class Step(
    val position: Pair<Int, Int>,
    val symbol: Char?,
    val comingFrom: Direction?
) {
    override fun toString(): String {
        return "Step(position=$position, symbol=$symbol, comingFrom=$comingFrom)"
    }
}

val directionsMap = mapOf(
    'L' to Pair(Direction.North, Direction.East),
    'J' to Pair(Direction.North, Direction.West),
    '7' to Pair(Direction.South, Direction.West),
    'F' to Pair(Direction.South, Direction.East)
)

enum class Direction { North, East, South, West, Undefined }

fun tenth(): Int {
    var current = Step(getStartPosition(input), null, null)
    val isPartOfLoop: MutableList<MutableList<Boolean>> =
        MutableList(input.size) { MutableList(input[0].length) { false } }
    var steps = 0
    while (current.symbol != 'S') {
        isPartOfLoop[current.position.first][current.position.second] = true
        val (nextSymbol, nextComingFrom) = getNext(current)
        current = Step(getNextPosition(current.position, nextComingFrom), nextSymbol, getOpposite(nextComingFrom))
        steps++
    }

    val furthestDistance = steps / 2 //Part 1

    var area = 0
    for (i in input.indices)
        for (j in input[i].indices)
            if (!isPartOfLoop[i][j] && amountVerticalWallsInLine(isPartOfLoop[i], input[i].take(j)) % 2 != 0)
                area++

    return area
}

private fun amountVerticalWallsInLine(isPartOfLoop: List<Boolean>, leftLine: String): Int {
    var pipes = 0
    var ls = 0
    var js = 0
    var sevens = 0
    var fs = 0
    for (i in leftLine.indices)
        if (isPartOfLoop[i])
            when (leftLine[i]) {
                '|' -> pipes++
                'L' -> ls++
                'J' -> js++
                '7' -> sevens++
                'F' -> fs++
            }
    return pipes + abs(ls - js + sevens - fs) / 2 // Coming from top left to bottom right increases count
}

private fun getData(input: List<String>): List<String> {
    val horizontalBorder = listOf(".".repeat(input[0].length + 2))
    return horizontalBorder + input.map { ".$it." } + horizontalBorder
}

private fun getStartPosition(map: List<String>): Pair<Int, Int> {
    val line = map.find { it.contains('S') }.orEmpty()
    return (Pair(map.indexOf(line), line.indexOf('S')))
}

private fun getNext(step: Step): Pair<Char, Direction> {
    if (step.symbol == null || step.comingFrom == null)
        return getFirst(step.position)

    val newComingFrom = getNewComingFrom(step.comingFrom, step.symbol)
    val newSymbol = getByDirection(step.position, newComingFrom)
    return Pair(newSymbol, newComingFrom)
}

private fun getFirst(position: Pair<Int, Int>): Pair<Char, Direction> {
    val validNeighbours = mutableMapOf(
        Direction.North to getNorth(position),
        Direction.East to getEast(position),
        Direction.South to getSouth(position),
        Direction.West to getWest(position)
    )

    return validNeighbours
        .filter {
            it.key == Direction.North && it.value in listOf('7', '|', 'F')
                    || it.key == Direction.East && it.value in listOf('J', '-', '7')
                    || it.key == Direction.South && it.value in listOf('J', '|', 'L')
                    || it.key == Direction.West && it.value in listOf('L', '-', 'F')
        }
        .map { Pair(it.value, it.key) }[0]

}

private fun getNorth(position: Pair<Int, Int>): Char = input[position.first - 1][position.second]
private fun getEast(position: Pair<Int, Int>): Char = input[position.first][position.second + 1]
private fun getSouth(position: Pair<Int, Int>): Char = input[position.first + 1][position.second]
private fun getWest(position: Pair<Int, Int>): Char = input[position.first][position.second - 1]

private fun getByDirection(position: Pair<Int, Int>, direction: Direction): Char =
    when (direction) {
        Direction.North -> getNorth(position)
        Direction.East -> getEast(position)
        Direction.South -> getSouth(position)
        Direction.West -> getWest(position)
        Direction.Undefined -> throw IllegalArgumentException()
    }

private fun getOpposite(direction: Direction): Direction {
    return when (direction) {
        Direction.North -> Direction.South
        Direction.East -> Direction.West
        Direction.South -> Direction.North
        Direction.West -> Direction.East
        Direction.Undefined -> throw IllegalArgumentException()
    }
}

private fun getNextPosition(oldPosition: Pair<Int, Int>, goingTo: Direction): Pair<Int, Int> =
    when (goingTo) {
        Direction.North -> Pair(oldPosition.first - 1, oldPosition.second)
        Direction.East -> Pair(oldPosition.first, oldPosition.second + 1)
        Direction.South -> Pair(oldPosition.first + 1, oldPosition.second)
        Direction.West -> Pair(oldPosition.first, oldPosition.second - 1)
        Direction.Undefined -> throw IllegalArgumentException()
    }

private fun getNewComingFrom(comingFrom: Direction, symbol: Char): Direction {
    if (symbol in listOf('-', '|'))
        return getOpposite(comingFrom)
    return mapDirections(comingFrom, symbol)
}

private fun mapDirections(oldDirection: Direction, symbol: Char): Direction {
    val mapping = directionsMap[symbol] ?: Pair(Direction.North, Direction.West)
    if (mapping.first == oldDirection) return mapping.second
    if (mapping.second == oldDirection) return mapping.first
    else throw IllegalArgumentException()
}
