package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.domain.CityRepository
import kotlinx.coroutines.coroutineScope

class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }


}