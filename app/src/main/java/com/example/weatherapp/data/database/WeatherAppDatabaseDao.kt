package com.example.weatherapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherAppDatabaseDao {
    @Insert
    suspend fun insert(city: FavoriteCities)

    @Update
    suspend fun update(city: FavoriteCities)

    @Delete
    suspend fun delete(city: FavoriteCities)

    @Query("SELECT * FROM favorite_cities_table ORDER BY id")
    fun getAllCities(): LiveData<List<FavoriteCities>>

    @Query("SELECT * FROM favorite_cities_table WHERE id = :key")
    suspend fun getCity(key: Long): FavoriteCities?

    @Query("DELETE FROM favorite_cities_table")
    fun clearAll()
}