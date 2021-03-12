package com.example.weatherapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cities_table")
data class FavoriteCity(
    @PrimaryKey()
    val id: Long
)
