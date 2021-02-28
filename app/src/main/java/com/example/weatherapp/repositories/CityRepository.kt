package com.example.weatherapp.repositories

import com.example.weatherapp.data.City
import com.example.weatherapp.data.Weather

class CityRepository {
    private val cities = mutableListOf<City>(
        City(name = "Moscow", country = "RU", Weather(-10, 748, 0.1f, "snowy")),
        City(name = "Athens", country = "GR", Weather(14, 762, 0f, "cloudy")),
        City(name = "Hong Kong", country = "HK", Weather(19, 760, 0f, "sunny")),
        City(name = "Rome", country = "IT", Weather(18, 764, 0f, "sunny")),
        City(name = "Saint-Petersburg", country = "RU", Weather(2, 763, 5.5f, "rainy")),
        City(name = "Warsaw", country = "PL", Weather(4, 763, 0f, "cloudy")),
        City(name = "New-York", country = "US", Weather(7, 770, 7f, "rainy")),
        City(name = "Paris", country = "FR", Weather(11, 772, 0f, "sunny")),
        City(name = "Tomsk", country = "RU", Weather(-21, 763, 0f, "cloudy"))
    )

    fun getCities(): List<City> = cities

    fun getCity(name: String): City? = cities.firstOrNull { it.name == name }
}