import java.io.File

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
//    'J' to 'D', // Part 1
    'J' to 'N', // Part 2
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

fun seventh(): Int = File("src/main/resources/input7").readLines()
    .map { it.split(" ") }
    .sortedWith(compareBy<List<String>> { getHand(it[0]) }.thenBy { mapCards(it[0]) })
    .reversed()
    .mapIndexed { i, it -> (i + 1) * it[1].toInt() }
    .sum()

private fun getHand(cards: String): Combination {
    val cardsCount = cards.groupBy { it }.mapValues { it.value.size }.toMutableMap()

// Part 1
//    val amountJokers = 0
/////////

// Part2
    val amountJokers = cardsCount['J'] ?: 0
    cardsCount.remove('J')
////////

    if (isFiveOAK(cardsCount, amountJokers)) return Combination.FIVE_OAK
    if (isFourOAK(cardsCount, amountJokers)) return Combination.FOUR_OAK
    if (isFullHouse(cardsCount, amountJokers)) return Combination.FULL_HOUSE
    if (isThreeOAK(cardsCount, amountJokers)) return Combination.THREE_OAK
    if (isTwoPairs(cardsCount, amountJokers)) return Combination.TWO_PAIR
    if (isOnePair(cardsCount, amountJokers)) return Combination.ONE_PAIR
    return Combination.HIGH_CARD
}

private fun isFiveOAK(cardsCount: Map<Char, Int>, amountJokers: Int): Boolean {
    return cardsCount.values.any { it == 5 }
            || (cardsCount.values.any { it == 4 } && amountJokers == 1)
            || (cardsCount.values.any { it == 3 } && amountJokers == 2)
            || (cardsCount.values.any { it == 2 } && amountJokers == 3)
            || (cardsCount.values.any { it == 1 } && amountJokers == 4)
            || amountJokers == 5
}

private fun isFourOAK(cardsCount: Map<Char, Int>, amountJokers: Int): Boolean {
    return (cardsCount.values.any { it == 4 } && amountJokers == 0)
            || (cardsCount.values.any { it == 3 } && amountJokers == 1)
            || (cardsCount.values.any { it == 2 } && amountJokers == 2)
            || (cardsCount.values.any { it == 1 } && amountJokers == 3)
            || amountJokers == 4
}

private fun isFullHouse(cardsCount: Map<Char, Int>, amountJokers: Int): Boolean {
    if (amountJokers == 0)
        return (cardsCount.values.any { it == 3 } && cardsCount.values.any { it == 2 })
    if (amountJokers == 1)
        return cardsCount.values.count { it == 2 } == 2
    return false
}

private fun isThreeOAK(cardsCount: Map<Char, Int>, amountJokers: Int): Boolean {
    return (cardsCount.values.any { it == 3 } && amountJokers == 0)
            || (cardsCount.values.any { it == 2 } && amountJokers == 1)
            || (cardsCount.values.any { it == 1 } && amountJokers == 2)
}

private fun isTwoPairs(cardsCount: Map<Char, Int>, amountJokers: Int): Boolean {
    return cardsCount.size == 3
            || (cardsCount.values.any { it == 2 } && amountJokers == 1)
            || amountJokers == 2
}

private fun isOnePair(cardsCount: Map<Char, Int>, amountJokers: Int): Boolean {
    return cardsCount.values.any { it == 2 }
            || amountJokers == 1
}

private fun mapCards(cards: String): String = cards.map { cardValueMap[it] ?: '0' }.toString()
