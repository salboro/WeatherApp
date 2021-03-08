package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCities
import com.example.weatherapp.data.network.City

interface CityRepository {

    suspend fun deleteFavoriteCity(id: Long)

    suspend fun getFavoriteCity(id: Long): FavoriteCities?

    suspend fun setFavoriteCity(id: Long)

    suspend fun getFavoriteCities(id: Long): List<FavoriteCities>

    suspend fun getCities(latitude: Double, longitude: Double): List<City>
}