package com.example.weatherapp.data

import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository

class CityRepositoryImpl(private val cityDataSource: CityDataSource): CityRepository {
    override fun getCities(): List<City> = cityDataSource.getCities()

    override fun getCity(name: String): City? = cityDataSource.getCity(name)
}