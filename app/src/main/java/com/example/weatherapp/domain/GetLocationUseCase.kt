package com.example.weatherapp.domain

import android.location.Location
import androidx.lifecycle.MutableLiveData

class GetLocationUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(location: MutableLiveData<Location?>) {
        cityRepository.getLocation(location)
    }
}