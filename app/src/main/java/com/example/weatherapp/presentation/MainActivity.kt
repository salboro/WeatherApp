package com.example.weatherapp.presentation

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.weatherapp.R
import com.example.weatherapp.data.database.CityWeather
import com.example.weatherapp.data.database.WeatherAppDatabase
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("lul", "another")
        setContentView(R.layout.activity_main)

        val dataSource = WeatherAppDatabase.getInstance(application).weatherAppDatabaseDao
        lifecycleScope.launch {
            populateCities(dataSource)
        }

        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }

    private suspend fun populateCities(database: WeatherAppDatabaseDao) {
        if (database.getCity(1) == null) {
            val cities = mutableListOf<CityWeather>(
                CityWeather(9, name = "Moscow", country = "RU", -10, 748, 0.1f, "snowy"),
                CityWeather(1, name = "Athens", country = "GR", 14, 762, 0f, "cloudy"),
                CityWeather(2, name = "Hong Kong", country = "HK", 19, 760, 0f, "sunny"),
                CityWeather(3, name = "Rome", country = "IT", 18, 764, 0f, "sunny"),
                CityWeather(4, name = "Saint-Petersburg", country = "RU", 2, 763, 5.5f, "rainy"),
                CityWeather(5, name = "Warsaw", country = "PL", 4, 763, 0f, "cloudy"),
                CityWeather(6, name = "New-York", country = "US", 7, 770, 7f, "rainy"),
                CityWeather(7, name = "Paris", country = "FR", 11, 772, 0f, "sunny"),
                CityWeather(8, name = "Tomsk", country = "RU", -21, 763, 0f, "cloudy")
            )
            database.insert(cities)
        }
    }
}