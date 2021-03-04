package com.example.weatherapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.GetCitiesUseCase

class ListViewModel(private val getCitiesUseCase: GetCitiesUseCase): ViewModel() {

    private val _cityList = MutableLiveData<List<City>>()

    val cityList: LiveData<List<City>>
        get() = _cityList

    fun getCities() {
        _cityList.value = getCitiesUseCase()
    }

}