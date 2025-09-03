package org.example

import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.io.File

fun main() = runBlocking {

    val apiFile = File("apikey.txt")
    if(!apiFile.exists()){
        error("Missing WeatherAPI key txt file.")
    }

    val apiKey = apiFile.readText().trim()
    val listOfCities = listOf("Chisinau", "Madrid", "Kyiv", "Amsterdam")

    val tomorrowDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                                        .format(LocalDate.now().plusDays(1))

    println("\n\nWeather forecast for $tomorrowDate\n")

    val colWidth = 14

    val header = String.format("%-${colWidth}s | ".repeat(6),
        "City Name",
        "MinTemp (°C)",
        "MaxTemp (°C)",
        "Humidity (%)",
        "Wind Spd (kph)",
        "Wind Direction")

    println(header)
    println("-".repeat(header.length - 1))

    for (city in listOfCities) {
        try {
            val response = RetrofitClient.instance.getForecast(apiKey, city)
            val tomorrow = response.forecast.forecastday[1]

            println(String.format("%-${colWidth}s | " + "%-${colWidth}.1f | ".repeat(4) + "%-${colWidth}s | ",
                response.location.name,
                tomorrow.day.mintemp_c,
                tomorrow.day.maxtemp_c,
                tomorrow.day.avghumidity,
                tomorrow.day.maxwind_kph,
                tomorrow.hour[0].wind_dir)) // take wind dir from the first hour of the day

        } catch (e: Exception) {
            println("Error retrieving weather for $city: ${e.message}")
        }
    }

    println("-".repeat(header.length - 1))

}