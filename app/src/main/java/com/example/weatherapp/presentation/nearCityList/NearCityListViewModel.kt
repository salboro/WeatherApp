package com.example.weatherapp.presentation.nearCityList

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetCitiesUseCase
import com.example.weatherapp.domain.GetLocationUseCase
import kotlinx.coroutines.launch

class NearCityListViewModel(
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getLocationUseCase: GetLocationUseCase
) : ViewModel() {

    private var _cityList = MutableLiveData<List<City>>()

    val cityList: LiveData<List<City>>
        get() = _cityList

    private var _location = MutableLiveData<Location?>()

    val location: LiveData<Location?>
        get() = _location

    fun getCities() {
        viewModelScope.launch {
            if (location.value != null) {
                _cityList.value =
                    getCitiesUseCase(_location.value!!.latitude, location.value!!.longitude)!!
            }
        }
    }

    fun setLocation() {
        viewModelScope.launch {
            getLocationUseCase(_location)
        }
    }
}