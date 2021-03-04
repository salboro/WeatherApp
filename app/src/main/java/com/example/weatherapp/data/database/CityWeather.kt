package com.example.weatherapp.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_in_city_table")
data class CityWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo(name = "city_name")
    val name: String,

    @ColumnInfo(name = "country_name")
    val country: String,

    @ColumnInfo(name = "degree_in_city")
    val degreesC: Int,

    @ColumnInfo(name = "pressure_in_city")
    val pressure: Int,

    @ColumnInfo(name = "sediment_in_city")
    val sediment: Float,

    @ColumnInfo(name = "weather_condition_in_city")
    val condition: String
)
