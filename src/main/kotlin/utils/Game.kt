package utils

data class Game(
    var id: Int,
    var maxRed: Int = 0,
    var maxGreen: Int = 0,
    var maxBlue: Int = 0
)

data class Draw(
    var red: Int = 0,
    var green: Int = 0,
    var blue: Int = 0,
)