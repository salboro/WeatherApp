package com.example.weatherapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetCitiesFromApiUseCase
import com.example.weatherapp.domain.GetCitiesUseCase
import kotlinx.coroutines.launch

class ListViewModel(private val getCitiesFromApiUseCase: GetCitiesFromApiUseCase): ViewModel() {


    private var _cityList = MutableLiveData<List<City>>()

    val cityList: LiveData<List<City>>
        get() = _cityList

    fun getCities() {
        viewModelScope.launch {
            _cityList.value = getCitiesFromApiUseCase()
        }
    }

}