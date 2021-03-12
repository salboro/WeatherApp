package com.example.weatherapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteCity::class], version = 1, exportSchema = false)
abstract class WeatherAppDatabase: RoomDatabase() {
    abstract val weatherAppDatabaseDao: WeatherAppDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherAppDatabase? = null

        fun getInstance(context: Context): WeatherAppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WeatherAppDatabase::class.java, "weatherApp_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}