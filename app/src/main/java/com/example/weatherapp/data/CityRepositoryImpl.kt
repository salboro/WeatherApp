package com.example.weatherapp.data

import android.util.Log
import com.example.weatherapp.data.database.FavoriteCities
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.domain.CityRepository

class CityRepositoryImpl(val database: WeatherAppDatabaseDao): CityRepository {

    override suspend fun getCity(id: Long): FavoriteCities? = database.getCity(id)

    override suspend fun getCities(latitude: Double, longitude: Double): List<City> {
        val list = try {
            WeatherApi.retrofitService.getCityList(latitude = latitude, longitude = longitude).list
        } catch (e: Exception) {
            Log.i("Error in getting cities", e.toString())
            ArrayList()
        }
        return list
    }
}