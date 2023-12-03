import utils.Draw
import utils.Game
import java.io.File

fun secondA(): Int =
    File("src/main/resources/input2").readLines()
        .map { entry -> parseGame(entry) }
        .filter { game -> isPossible(game) }
        .sumOf { game -> game.id }

fun secondB(): Int =
    File("src/main/resources/input2").readLines()
        .map { entry -> parseGame(entry) }
        .sumOf { game -> getPower(game) }

private fun parseGame(entry: String): Game {
    val splitInput = entry.split(": ")
    val id = splitInput[0].replace("Game ", "").toInt()
    val draws = splitInput[1].split("; ").map { draws -> parseDraw(draws) }
    val maxRed = draws.map { draw -> draw.red }.maxOrNull() ?: 0
    val maxGreen = draws.map { draw -> draw.green }.maxOrNull() ?: 0
    val maxBlue = draws.map { draw -> draw.blue }.maxOrNull() ?: 0
    return Game(id, maxRed, maxGreen, maxBlue)

}

private fun parseDraw(drawString: String): Draw {
    val draw = Draw();
    for (entry in drawString.split(", ")) {
        val splitEntry = entry.split(" ");
        when (splitEntry[1]) {
            "red" -> draw.red = splitEntry[0].toInt()
            "green" -> draw.green = splitEntry[0].toInt()
            "blue" -> draw.blue = splitEntry[0].toInt()
        }
    }
    return draw;
}

private fun isPossible(game: Game): Boolean = game.maxRed < 13 && game.maxGreen < 14 && game.maxBlue < 15

private fun getPower(game: Game): Int = game.maxRed * game.maxGreen * game.maxBlue