package com.example.weatherapp.domain

class SetFavoriteCityUseCase(private val cityRepository: CityRepository) {

    suspend operator fun invoke(id: Long, lang: String) {
        cityRepository.setFavoriteCity(id, lang)
    }
}