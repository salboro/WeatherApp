package com.example.weatherapp.data

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.FavoriteCities
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.domain.CityRepository

class CityRepositoryImpl(
    val database: WeatherAppDatabaseDao,
    private val locationService: WeatherAppLocationService
) : CityRepository {

    override suspend fun deleteFavoriteCity(id: Long) {
        database.delete(FavoriteCities(id))
    }

    override suspend fun getFavoriteCity(id: Long): FavoriteCities? = database.getFavoriteCity(id)

    override suspend fun setFavoriteCity(id: Long) {
        database.insert(FavoriteCities(id))
    }

    override suspend fun getFavoriteCitiesFromDB(): List<FavoriteCities>? =
        database.getFavoriteCities()

    override suspend fun getFavoriteCities(list: List<FavoriteCities>): List<City> {
        val favoriteCitiesToReturn = mutableListOf<City>()
        try {
            for (city in list) {

                favoriteCitiesToReturn.add(WeatherApi.retrofitService.getCity(id = city.id))
            }
        } catch (e: Exception) {
//            Log.i("Error in getting cities", e.toString())
            return ArrayList()
        }
        return favoriteCitiesToReturn.toList()
    }

    override suspend fun getCities(latitude: Double, longitude: Double): List<City> {
        val list = try {
            WeatherApi.retrofitService.getCityList(latitude = latitude, longitude = longitude).list
        } catch (e: Exception) {
//            Log.i("Error in getting cities", e.toString())
            ArrayList()
        }
        return list
    }

    override suspend fun getCityByName(cityName: String): City? {
        return try {
            WeatherApi.retrofitService.getCityByName(name = cityName)
        } catch (e: Exception) {
//            Log.i("Error in getting cities", e.toString())
            null
        }
    }

    override fun getLocation(location: MutableLiveData<Location?>) {
        locationService.getLastLocation {
            location.value = it
        }
    }

}