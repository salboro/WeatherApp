package com.example.weatherapp.presentation.favoriteCityList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetFavoriteCitiesFromDBUseCase
import com.example.weatherapp.domain.GetFavoriteCitiesUseCase
import kotlinx.coroutines.launch

class FavoriteCityListViewModel(
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
    private val getFavoriteCitiesFromDBUseCase: GetFavoriteCitiesFromDBUseCase
) : ViewModel() {

    private var _cityList = MutableLiveData<List<City>>()

    val cityList: LiveData<List<City>>
        get() = _cityList

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch {
            val citiesList = getFavoriteCitiesFromDBUseCase()
            if (citiesList != null) {
                Log.i("here", "again")
                _cityList.value = getFavoriteCitiesUseCase(citiesList)
            }
        }
    }

    fun refreshCities() {
        getCities()
    }
}
