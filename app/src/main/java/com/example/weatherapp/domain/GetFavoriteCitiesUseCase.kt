package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCities
import com.example.weatherapp.data.network.City

class GetFavoriteCitiesUseCase(private val cityRepository: CityRepository) {
    suspend operator fun invoke(list: List<FavoriteCities>): List<City> {
        return cityRepository.getFavoriteCities(list)
    }
}