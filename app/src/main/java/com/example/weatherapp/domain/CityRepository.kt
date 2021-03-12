package com.example.weatherapp.domain

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.FavoriteCity
import com.example.weatherapp.data.network.City

interface CityRepository {

    suspend fun deleteFavoriteCity(id: Long)

    suspend fun getFavoriteCity(id: Long): FavoriteCity?

    suspend fun setFavoriteCity(id: Long)

    suspend fun getFavoriteCities(list: List<FavoriteCity>): List<City>

    suspend fun getFavoriteCitiesFromDB(): List<FavoriteCity>?

    suspend fun getCities(latitude: Double, longitude: Double): List<City>

    suspend fun getCityByName(cityName: String): City?

    fun getLocation(location: MutableLiveData<Location?>)
}