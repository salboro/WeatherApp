package com.example.weatherapp.domain

class DeleteFavoriteCityUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(id: Long) {
        cityRepository.deleteFavoriteCity(id)
    }
}