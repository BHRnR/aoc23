import utils.Card
import java.io.File
import kotlin.math.pow

fun fourthA(): Int =
    File("src/main/resources/input4").readLines()
        .map { parseCard(it) }
        .sumOf { 2.0.pow(it.matches - 1).toInt() }

fun fourthB(): Int {
    val cards = File("src/main/resources/input4").readLines().map { parseCard(it) }
    for (card in cards) {
        card.copies++
        for (upcoming in card.id until card.id + card.matches)
            cards[upcoming].copies += card.copies
    }
    return cards.sumOf { it.copies }
}

private fun parseCard(entry: String): Card {
    val splitInput = entry.split(":")
    val id = splitInput[0].split(" ").last.toInt()
    val numbers = splitInput[1].split(" | ")
    val winningNumbers = parseList(numbers[0])
    val ownNumbers = parseList(numbers[1])
    return Card(id, winningNumbers, ownNumbers, getMatches(winningNumbers, ownNumbers))
}

private fun parseList(list: String): Set<Int> =
    list.split(" ")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toSet()

private fun getMatches(winningNumbers: Set<Int>, ownNumbers: Set<Int>): Int =
    (winningNumbers.intersect(ownNumbers).size)
