package com.example.weatherapp.presentation.searchCity

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetCityByNameUseCase
import kotlinx.coroutines.launch

class SearchCityFragmentViewModel(
    private val getCityByNameUseCase: GetCityByNameUseCase,
    private val context: Context
) : ViewModel() {

    private var _city = MutableLiveData<City?>()

    val city: LiveData<City?>
        get() = _city

    fun getCity(name: String) {
        viewModelScope.launch {
            val city = getCityByNameUseCase(name)
            if (city != null) {
                _city.value = city!!
            } else {
                Toast.makeText(context, "We can't find this city", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun doneNavigating() {
        _city.value = null
    }
}