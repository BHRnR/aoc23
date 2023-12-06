import utils.SeedMap
import utils.SeedMapEntry
import java.io.File

fun fifth(): Long {
    val input = parseInput()
    var minLocation = input.humidityLocationMap.maxOf { it.destinationStart }
    var currentSeed = 0
    val seeds = input.seeds

/////////////////////////////
//    Part 1
//    val totalSeeds = seeds.size.toLong()
/////////////////////////////

/////////////////////////////
//     Part 2
    val pairs = seeds.chunked(2)
    val totalSeeds = pairs.sumOf { it[1] }
    for (pair in pairs) {
        for (seed in pair[0] until pair[0] + pair[1]) {
/////////////////////////////

/////////////////////////////
//    Part 1
//            for (seed in seeds) {
/////////////////////////////

            if (currentSeed % (totalSeeds / 1000).toInt() == 0)
                println("${currentSeed.toDouble() / totalSeeds.toDouble() * 100}% done")

            currentSeed++
            val soil = mapValue(seed, input.seedSoilMap)
            val fertilizer = mapValue(soil, input.soilFertilizerMap)
            val water = mapValue(fertilizer, input.fertilizerWaterMap)
            val light = mapValue(water, input.waterLightMap)
            val temperature = mapValue(light, input.lightTemperatureMap)
            val humidity = mapValue(temperature, input.temperatureHumidityMap)
            val location = mapValue(humidity, input.humidityLocationMap)

            if (location < minLocation)
                minLocation = location
            currentSeed++
        }

/////////////////////////////
// Part 2
    }
/////////////////////////////

    return minLocation
}


private fun parseInput(): SeedMap {
    val input = File("src/main/resources/input5").readLines()
        .filter { it.isNotEmpty() } + listOf("DATA END")
    return SeedMap(
        input[0].split(": ")[1].split(" ").map { it.toLong() },
        parseMap(input, "seed-to-soil map:"),
        parseMap(input, "soil-to-fertilizer map:"),
        parseMap(input, "fertilizer-to-water map:"),
        parseMap(input, "water-to-light map:"),
        parseMap(input, "light-to-temperature map:"),
        parseMap(input, "temperature-to-humidity map:"),
        parseMap(input, "humidity-to-location map:")
    )
}

private fun parseMap(input: List<String>, title: String): List<SeedMapEntry> {
    var currentLine = input.indexOf(title) + 1
    val entries = mutableListOf<SeedMapEntry>()
    while (input[currentLine][0].isDigit()) {
        val data = input[currentLine].split(" ").map { it.toLong() }
        entries.add(SeedMapEntry(data[0], data[1], data[2]))
        currentLine++
    }
    return entries
}

private fun mapValue(source: Long, seedMapEntries: List<SeedMapEntry>): Long {
    for (entry in seedMapEntries)
        if (source in entry.sourceStart until entry.sourceStart + entry.range)
            return source - entry.sourceStart + entry.destinationStart
    return source
}
