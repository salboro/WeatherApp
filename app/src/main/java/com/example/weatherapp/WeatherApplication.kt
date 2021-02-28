package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.repositories.CityRepository

class WeatherApplication: Application() {

    lateinit var cityRepository: CityRepository

    override fun onCreate() {
        super.onCreate()
        cityRepository = CityRepository()
    }
}