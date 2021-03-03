package com.example.weatherapp.data

import com.example.weatherapp.domain.City

interface CityDataSource {

    fun getCities(): List<City>

    fun getCity(cityName: String): City?
}