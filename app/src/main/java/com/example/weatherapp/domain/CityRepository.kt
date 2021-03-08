package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCities
import com.example.weatherapp.data.network.City

interface CityRepository {

    suspend fun setFavoriteCity(id: Long)

    suspend fun getCity(id: Long): FavoriteCities?

    suspend fun getCities(latitude: Double, longitude: Double): List<City>
}