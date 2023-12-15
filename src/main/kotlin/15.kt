import java.io.File


class Lens(
    val label: String,
    val focal: Int
)

class Box(
    val lenses: MutableList<Lens>
)

fun fifteenth(): Int {
    val input = File("src/main/resources/input15").readText().split(",").map { it.trim() }

//    return input.sumOf { getHashForString(it) } //Part 1

    val boxes = List(256) { Box(mutableListOf()) }
    for (operation in input)
        handleOperation(boxes, operation)

    return boxes.asSequence()
        .mapIndexed { iBox, box -> getBoxPower(box, iBox) }
        .sum()
}

private fun handleOperation(boxes: List<Box>, operation: String) {
    val typeIndex = operation.indexOfFirst { !it.isLetter() }
    val label = operation.take(typeIndex)
    val box = boxes[getHashForString(label)]

    if (operation[typeIndex] == '-')
        box.lenses.removeAll { it.label == label }

    if (operation[typeIndex] == '=') {
        val indexOfLabel = box.lenses.indexOfFirst { it.label == label }
        if (indexOfLabel != -1)
            box.lenses[indexOfLabel] = Lens(label, operation[typeIndex + 1].toString().toInt())
        else
            box.lenses.add(Lens(label, operation[typeIndex + 1].toString().toInt()))
    }
}

private fun getHashForString(line: String): Int =
    line.fold(0) { acc, c -> getHashForChar(acc, c) }

private fun getHashForChar(oldValue: Int, char: Char): Int =
    ((oldValue + char.code) * 17) % 256

private fun getBoxPower(box: Box, iBox: Int): Int =
    box.lenses.indices.fold(0) { acc, iLens -> acc + (iBox + 1) * (iLens + 1) * box.lenses[iLens].focal }

