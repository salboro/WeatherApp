package com.example.weatherapp.domain

data class Weather(
    val degreesC: Int,
    val pressure: Int,
    val sediment: Float,
    val condition: String
)
