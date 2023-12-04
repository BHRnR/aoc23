import utils.Card
import java.io.File
import kotlin.math.pow

fun fourthA(): Int =
    File("src/main/resources/input4").readLines()
        .map { parseCard(it) }
        .sumOf { getPointsPerCard(it) }

private fun parseCard(entry: String): Card {
    val numbers = entry.split(": ")[1].split(" | ")
    return Card(parseList(numbers[0]), parseList(numbers[1]))
}

private fun parseList(list: String): Set<Int> =
    list.split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toSet()

private fun getPointsPerCard(card: Card): Int {
    val matches = card.ownNumbers.intersect(card.winningNumbers).size
    return 2.0.pow(matches - 1).toInt()
}
