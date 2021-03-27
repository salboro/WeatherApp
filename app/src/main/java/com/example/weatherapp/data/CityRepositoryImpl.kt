package com.example.weatherapp.data

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.data.database.FavoriteCity
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.ForecastList
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.domain.CityRepository

class CityRepositoryImpl(
    val database: WeatherAppDatabaseDao,
    private val locationService: WeatherAppLocationService
) : CityRepository {

    override suspend fun deleteFavoriteCity(id: Long) {
        database.delete(FavoriteCity(id))
    }

    override suspend fun getFavoriteCity(id: Long): FavoriteCity? = database.getFavoriteCity(id)

    override suspend fun setFavoriteCity(id: Long) {
        database.insert(FavoriteCity(id))
    }

    override suspend fun getFavoriteCitiesFromDB(): List<FavoriteCity>? =
        database.getFavoriteCities()

    override suspend fun getFavoriteCities(list: List<FavoriteCity>): List<City> {
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
        return try {
            WeatherApi.retrofitService.getCityList(latitude = latitude, longitude = longitude).list
        } catch (e: Exception) {
//            Log.i("Error in getting cities", e.toString())
            ArrayList()
        }
    }

    override suspend fun getCityByName(cityName: String): City? {
        return try {
            WeatherApi.retrofitService.getCityByName(name = cityName)
        } catch (e: Exception) {
//            Log.i("Error in getting cities", e.toString())
            null
        }
    }

    override suspend fun getForecast(cityId: Long): ForecastList? {
        return try {
            WeatherApi.retrofitService.getCityForecast(cityId)
        } catch (e: Exception) {
            Log.i("Error in forecast", e.toString())
            null
        }
    }

    override suspend fun getLocation(location: MutableLiveData<Location?>) {
        locationService.getLastLocation {
            location.value = it
        }
    }

}