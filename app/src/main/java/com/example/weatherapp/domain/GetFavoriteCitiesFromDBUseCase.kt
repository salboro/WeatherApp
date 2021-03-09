package com.example.weatherapp.domain

import com.example.weatherapp.data.database.FavoriteCities

class GetFavoriteCitiesFromDBUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(): List<FavoriteCities>? {
        return cityRepository.getFavoriteCitiesFromDB()
    }
}