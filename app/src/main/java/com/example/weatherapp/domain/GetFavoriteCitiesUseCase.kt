package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCity
import com.example.weatherapp.data.network.City

class GetFavoriteCitiesUseCase(private val cityRepository: CityRepository) {
    suspend operator fun invoke(list: List<FavoriteCity>): List<City> {
        return cityRepository.getFavoriteCities(list)
    }
}