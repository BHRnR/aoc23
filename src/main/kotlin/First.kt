import java.io.File

fun first(): Int =
    File("src/main/resources/input1").readLines()
//        .map { entry -> replaceWrittenWithChar(entry) } //uncoment for second part
        .map { entry -> getDigits(entry) }
        .map { entry -> combineDigits(entry) }
        .sumOf { line -> line.toInt() }

fun replaceWrittenWithChar(entry: String): String =
    entry.replace("one", "o1e")
        .replace("two", "t2o")
        .replace("three", "t3e")
        .replace("four", "f4r")
        .replace("five", "f5e")
        .replace("six", "s6x")
        .replace("seven", "s7n")
        .replace("eight", "e8t")
        .replace("nine", "n9e")

fun getDigits(entry: String): String =
    entry.filter { char -> char.isDigit() }

fun combineDigits(entry: String): String =
    entry.first().toString() + entry.last()
