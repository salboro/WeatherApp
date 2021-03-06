package com.example.weatherapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.DeleteFavoriteCityUseCase
import com.example.weatherapp.domain.GetFavoriteCityUseCase
import com.example.weatherapp.domain.SetFavoriteCityUseCase
import kotlinx.coroutines.launch

class DetailViewModel(
    private val viewCity: City,
    private val getFavoriteCityUseCase: GetFavoriteCityUseCase,
    private val setFavoriteCityUseCase: SetFavoriteCityUseCase,
    private val deleteFavoriteCityUseCase: DeleteFavoriteCityUseCase
) : ViewModel() {

    private var _city = MutableLiveData<City>()

    val city: LiveData<City>
        get() = _city

    private var _isFavoriteCity = MutableLiveData<Boolean>()

    val isFavoriteCity: LiveData<Boolean>
        get() = _isFavoriteCity

    init {
        _city.value = viewCity
        viewModelScope.launch {
            _isFavoriteCity.value = getFavoriteCityUseCase(viewCity.id) != null
        }
    }

    fun setFavoriteCityCondition() {
        viewModelScope.launch {
            if (getFavoriteCityUseCase(viewCity.id) == null) {
                setFavoriteCityUseCase(viewCity.id)
                _isFavoriteCity.value = true
            } else {
                deleteFavoriteCityUseCase(viewCity.id)
                _isFavoriteCity.value = false
            }
        }
    }
}