package com.example.weatherapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.domain.CityRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CityRepositoryImpl(val database: WeatherAppDatabaseDao): CityRepository {

    override fun getCities(): LiveData<List<CityWeather>> = database.getAllCities()

    override suspend fun getCity(id: Long): CityWeather? = database.getCity(id)
}