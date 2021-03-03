package com.example.weatherapp.domain

interface CityRepository {

    fun getCities(): List<City>

    fun getCity(name: String): City?
}