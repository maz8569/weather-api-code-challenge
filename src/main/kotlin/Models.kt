package org.example

data class WeatherResponse(
    val location: Location,
    val forecast: Forecast
)

data class Location(val name: String)

data class Forecast(val forecastday: List<ForecastDay>)

data class ForecastDay(
    val date: String,
    val day: Day,
    val hour: List<Hour>
)

data class Day(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val avghumidity: Float,
    val maxwind_kph: Float,
)

data class Hour(val wind_dir: String)
