package com.example.weatherapp.presentation.forecast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.ForecastList
import com.example.weatherapp.domain.GetForecastUseCase
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val city: City,
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _forecast = MutableLiveData<ForecastList>()

    val forecast: LiveData<ForecastList>
        get() = _forecast

    init {
        viewModelScope.launch {
            val forecast = getForecastUseCase(city.id)
            if (forecast != null) {
                _forecast.value = forecast!!
            }
        }
    }

}