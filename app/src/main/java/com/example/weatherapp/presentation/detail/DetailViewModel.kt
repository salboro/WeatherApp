package com.example.weatherapp.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetCityUseCase
import kotlinx.coroutines.launch

class DetailViewModel(city: City): ViewModel() {

    private var _city = MutableLiveData<City>()

    val city: LiveData<City>
        get() = _city

    init {
            _city.value = city
    }
}