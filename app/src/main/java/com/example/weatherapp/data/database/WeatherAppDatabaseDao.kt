package com.example.weatherapp.data.database

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
    suspend fun getFavoriteCities(): List<FavoriteCities>?

    @Query("SELECT * FROM favorite_cities_table WHERE id = :key")
    suspend fun getFavoriteCity(key: Long): FavoriteCities?

    @Query("DELETE FROM favorite_cities_table")
    fun clearAll()
}