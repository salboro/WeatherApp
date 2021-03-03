package com.example.weatherapp.presentation.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.City
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.GetCitiesUseCase

class ListViewModel(private val getCitiesUseCase: GetCitiesUseCase): ViewModel() {

    val cityList = MutableLiveData<List<City>>()

    fun getCities() {
        cityList.value = getCitiesUseCase()
    }

}