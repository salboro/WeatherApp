package com.example.weatherapp.presentation.nearCityList

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetCitiesUseCase
import kotlinx.coroutines.launch

class NearCityListViewModel(private val getCitiesUseCase: GetCitiesUseCase) : ViewModel() {

    private var _cityList = MutableLiveData<List<City>>()

    val cityList: LiveData<List<City>>
        get() = _cityList

    private lateinit var location: Location

    var latitude: Double = 0.0
    var longitude: Double = 0.0

    private fun getCities() {
        viewModelScope.launch {
            _cityList.value = getCitiesUseCase(location.latitude, location.longitude)
        }
    }

    fun fetchLocation(location: Location) {
        this.location = location
        getCities()
    }


}