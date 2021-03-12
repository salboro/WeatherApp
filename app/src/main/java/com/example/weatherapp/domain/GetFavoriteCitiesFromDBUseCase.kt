package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCity

class GetFavoriteCitiesFromDBUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(): List<FavoriteCity>? {
        return cityRepository.getFavoriteCitiesFromDB()
    }
}