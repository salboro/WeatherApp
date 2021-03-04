package com.example.weatherapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.CityWeather

interface CityRepository {

    fun getCities(): LiveData<List<CityWeather>>

    suspend fun getCity(id: Long): CityWeather?
}