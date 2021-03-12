package com.example.weatherapp.data.database

import androidx.room.*

@Dao
interface WeatherAppDatabaseDao {
    @Insert
    suspend fun insert(city: FavoriteCity)

    @Update
    suspend fun update(city: FavoriteCity)

    @Delete
    suspend fun delete(city: FavoriteCity)

    @Query("SELECT * FROM favorite_cities_table ORDER BY id")
    suspend fun getFavoriteCities(): List<FavoriteCity>?

    @Query("SELECT * FROM favorite_cities_table WHERE id = :key")
    suspend fun getFavoriteCity(key: Long): FavoriteCity?

    @Query("DELETE FROM favorite_cities_table")
    fun clearAll()
}