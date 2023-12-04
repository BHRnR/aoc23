package utils

data class Card(
    val id: Int,
    val winningNumbers: Set<Int>,
    val ownNumbers: Set<Int>,
    val matches: Int,
    var copies: Int = 0
)
