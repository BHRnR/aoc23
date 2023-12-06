import utils.SeedMap
import utils.SeedMapEntry
import java.io.File

fun fifth(): Long {
    val input = parseInput()
    var minLocation = input.humidityLocationMap.maxOf { it.destinationStart }
    val seeds = input.seeds

//////////////////////////////////////////
//     Part 1
//    for (seed in seeds) {
//        val soil = mapValue(seed, input.seedSoilMap)
//        val fertilizer = mapValue(soil, input.soilFertilizerMap)
//        val water = mapValue(fertilizer, input.fertilizerWaterMap)
//        val light = mapValue(water, input.waterLightMap)
//        val temperature = mapValue(light, input.lightTemperatureMap)
//        val humidity = mapValue(temperature, input.temperatureHumidityMap)
//        val location = mapValue(humidity, input.humidityLocationMap)
//
//        if (location < minLocation)
//            minLocation = location
//    }
//////////////////////////////////////////
//
//     Part 2
    val pairs = seeds.chunked(2)
    val totalSeeds = pairs.sumOf { it[1] }
    var currentSeed = 0
    for (pair in pairs) {
        var toSkip: Long = 0
        for (seed in pair[0]..<pair[0] + pair[1]) {
            println("Checking seed $currentSeed of $totalSeeds: ${(currentSeed.toDouble() / totalSeeds.toDouble()) * 100}% done. Current Minimum: $minLocation. Still to skip: $toSkip")
            currentSeed++

            if (toSkip > 0) {
                toSkip--
            } else {
                val soil = mapValue(seed, input.seedSoilMap)
                val fertilizer = mapValue(soil.first, input.soilFertilizerMap)
                val water = mapValue(fertilizer.first, input.fertilizerWaterMap)
                val light = mapValue(water.first, input.waterLightMap)
                val temperature = mapValue(light.first, input.lightTemperatureMap)
                val humidity = mapValue(temperature.first, input.temperatureHumidityMap)
                val location = mapValue(humidity.first, input.humidityLocationMap)

                toSkip = listOf(
                    soil.second,
                    fertilizer.second,
                    water.second,
                    light.second,
                    temperature.second,
                    humidity.second,
                    location.second
                ).min()

                if (location.first < minLocation)
                    minLocation = location.first
            }
        }
//////////////////////////////////////////
    }
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

private fun mapValue(source: Long, seedMapEntries: List<SeedMapEntry>): Pair<Long, Long> {
    for (entry in seedMapEntries)
        if (source in entry.sourceStart..entry.sourceStart + entry.range) {
            val target = source - entry.sourceStart + entry.destinationStart
            val restInRange = entry.sourceStart + entry.range - source
            return Pair(target, restInRange)
        }
    return Pair(source, 0)
}
