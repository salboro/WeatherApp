package com.example.weatherapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.data.network.WeatherApiService
import com.example.weatherapp.domain.CityRepository
import java.lang.Exception

class CityRepositoryImpl(val database: WeatherAppDatabaseDao): CityRepository {

    override fun getCities(): LiveData<List<CityWeather>> = database.getAllCities()

    override suspend fun getCity(id: Long): CityWeather? = database.getCity(id)

    override suspend fun getCitiesFromApi() : List<City> {
        val list = try {
            WeatherApi.retrofitService.getCityList().list
        } catch (e: Exception) {
            Log.i("asfa", e.toString())
            ArrayList()
        }
        return list
    }
}