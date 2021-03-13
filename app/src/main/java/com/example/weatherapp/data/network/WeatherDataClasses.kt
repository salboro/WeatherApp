package com.example.weatherapp.data.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

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
data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Items(
    val items: List<Item>
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Item(
    val link: String
) : Parcelable

