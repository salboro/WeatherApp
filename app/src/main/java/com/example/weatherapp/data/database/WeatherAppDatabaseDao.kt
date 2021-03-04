package com.example.weatherapp.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WeatherAppDatabaseDao {
    @Insert
    suspend fun insert(cityWeather: CityWeather)

    @Insert
    suspend fun insert(citiesWeather: List<CityWeather>)

    @Update
    suspend fun update(cityWeather: CityWeather)

    @Query("SELECT * FROM weather_in_city_table ORDER BY id")
    fun getAllCities(): LiveData<List<CityWeather>>

    @Query("SELECT * FROM weather_in_city_table WHERE id = :key")
    suspend fun getCity(key: Long): CityWeather?

    @Query("DELETE FROM weather_in_city_table")
    fun clearAll()
}