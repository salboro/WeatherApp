package com.example.weatherapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.domain.GetCitiesUseCase
import kotlinx.coroutines.launch

class ListViewModel(private val getCitiesUseCase: GetCitiesUseCase): ViewModel() {


    private var _cityList = getCitiesUseCase()

    val cityList: LiveData<List<CityWeather>>
        get() = _cityList

    fun getCities() {
        _cityList = getCitiesUseCase()
    }

}