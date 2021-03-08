package com.example.weatherapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.network.City

class DetailViewModel(city: City): ViewModel() {

    private var _city = MutableLiveData<City>()

    val city: LiveData<City>
        get() = _city

    init {
            _city.value = city
    }
}