package com.example.weatherapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.network.City

interface CityRepository {

    fun getCities(): LiveData<List<CityWeather>>

    suspend fun getCity(id: Long): CityWeather?

    suspend fun getCitiesFromApi(): List<City>
}