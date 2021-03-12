package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCity

class GetFavoriteCityUseCase(private val cityRepository: CityRepository) {
    suspend operator fun invoke(id: Long): FavoriteCity? {
        return cityRepository.getFavoriteCity(id)
    }
}