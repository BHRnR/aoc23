import java.io.File

fun firstA(): Int =
    File("src/main/resources/input1").readLines()
        .map { entry -> getDigits(entry) }
        .map { entry -> combineDigits(entry) }
        .sumOf { line -> line.toInt() }

fun firstB(): Int =
    File("src/main/resources/input1").readLines()
        .map { entry -> replaceWrittenWithChar(entry) }
        .map { entry -> getDigits(entry) }
        .map { entry -> combineDigits(entry) }
        .sumOf { line -> line.toInt() }

private fun replaceWrittenWithChar(entry: String): String =
//    first and last char have to be kept to make sure adjacent numbers are recognized
    entry.replace("one", "o1e")
        .replace("two", "t2o")
        .replace("three", "t3e")
        .replace("four", "f4r")
        .replace("five", "f5e")
        .replace("six", "s6x")
        .replace("seven", "s7n")
        .replace("eight", "e8t")
        .replace("nine", "n9e")

private fun getDigits(entry: String): String =
    entry.filter { char -> char.isDigit() }

private fun combineDigits(entry: String): String =
    entry.first().toString() + entry.last()
