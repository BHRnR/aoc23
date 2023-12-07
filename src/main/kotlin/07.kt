import java.io.File
import kotlin.time.times

enum class Combination(val value: Int) {
    FIVE_OAK(6),
    FOUR_OAK(5),
    FULL_HOUSE(4),
    THREE_OAK(3),
    TWO_PAIR(2),
    ONE_PAIR(1),
    HIGH_CARD(0)
}

val cardValueMap = mapOf(
    'A' to 'A',
    'K' to 'B',
    'Q' to 'C',
    'J' to 'D',
    'T' to 'E',
    '9' to 'F',
    '8' to 'G',
    '7' to 'H',
    '6' to 'I',
    '5' to 'J',
    '4' to 'K',
    '3' to 'L',
    '2' to 'M'
)

fun seventhA(): Int = File("src/main/resources/input7").readLines()
    .map { it.split(" ") }
    .sortedWith(compareBy<List<String>> { getHand(it[0]) }.thenBy { mapCards(it[0]) })
    .reversed()
    .mapIndexed { i, it -> (i + 1) * it[1].toInt() }
    .sum()


private fun getHand(cards: String): Combination {
    val charCounts = cards.groupBy { it }.mapValues { it.value.size }.values
    if (charCounts.size == 1)
        return Combination.FIVE_OAK
    if (charCounts.any { it == 4 })
        return Combination.FOUR_OAK
    if (charCounts.any { it == 3 })
        return if (charCounts.any { it == 2 }) Combination.FULL_HOUSE
        else Combination.THREE_OAK
    if (charCounts.any { it == 2 })
        return if (charCounts.size == 3) Combination.TWO_PAIR
        else Combination.ONE_PAIR
    else
        return Combination.HIGH_CARD
}

private fun mapCards(cards: String): String = cards.map { cardValueMap[it] ?: '0' }.toString()

