package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCities

class GetFavoriteCityUseCase(private val cityRepository: CityRepository) {
    suspend operator fun invoke(id: Long): FavoriteCities? {
        return cityRepository.getFavoriteCity(id)
    }
}