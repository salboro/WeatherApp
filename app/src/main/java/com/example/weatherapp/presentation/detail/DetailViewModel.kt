package com.example.weatherapp.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.domain.GetCityUseCase
import kotlinx.coroutines.launch

class DetailViewModel(getCityUseCase: GetCityUseCase, cityId: Long = 0L): ViewModel() {

    private var _city = MutableLiveData<CityWeather>()

    val city: LiveData<CityWeather>
        get() = _city

    init {
        viewModelScope.launch {
            _city.value = getCityUseCase(cityId)
            Log.i("db", "gotHim")
        }
    }
}