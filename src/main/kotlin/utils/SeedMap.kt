package utils

data class SeedMap(
    val seeds: List<Long>,
    val seedSoilMap: List<SeedMapEntry>,
    val soilFertilizerMap: List<SeedMapEntry>,
    val fertilizerWaterMap: List<SeedMapEntry>,
    val waterLightMap: List<SeedMapEntry>,
    val lightTemperatureMap: List<SeedMapEntry>,
    val temperatureHumidityMap: List<SeedMapEntry>,
    val humidityLocationMap: List<SeedMapEntry>
)

data class SeedMapEntry(
    val destinationStart: Long,
    val sourceStart: Long,
    val range: Long
)
