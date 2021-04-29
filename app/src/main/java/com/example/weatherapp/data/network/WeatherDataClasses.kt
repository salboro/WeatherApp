package com.example.weatherapp.data.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class ForecastList(
    val list: List<Forecast>,
    val city: CityForForecast
)

@JsonClass(generateAdapter = true)
data class CityForForecast(
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
)

@JsonClass(generateAdapter = true)
data class Forecast(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Cloud,
    val wind: Wind,
    val rain: RainForForecast?,
    val snow: SnowForForecast?
)

@JsonClass(generateAdapter = true)
data class Cities(
    val list: List<City>
)

@Parcelize
@JsonClass(generateAdapter = true)
data class City(
    val id: Long,
    val name: String,
    @Json(name = "coord") val coordinate: Coordinate,
    val main: Main,
    val wind: Wind,
    val clouds: Cloud,
    @Json(name = "sys") val country: Country,
    val rain: Rain?,
    val snow: Snow?,
    val weather: List<Weather>
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Coordinate(
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Main(
    @Json(name = "temp") val temperature: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Wind(
    val speed: Double
): Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Country(
    @Json(name = "country") val name: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Rain(
    @Json(name = "1h") val forOneHour: Double
) : Parcelable

@JsonClass(generateAdapter = true)
data class RainForForecast(
    @Json(name = "3h") val forThreeHours: Double
)

@Parcelize
@JsonClass(generateAdapter = true)
data class Cloud(
    @Json(name = "all") val cloudiness: Int
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Snow(
    @Json(name = "1h") val forOneHour: Double
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class SnowForForecast(
    @Json(name = "3h") val forThreeHours: Double
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable
