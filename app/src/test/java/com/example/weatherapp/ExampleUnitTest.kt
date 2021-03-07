package com.example.weatherapp

import com.example.weatherapp.data.CityRepositoryImpl
import com.example.weatherapp.data.network.City
import com.example.weatherapp.data.network.WeatherApi
import com.example.weatherapp.domain.CityRepository
import com.example.weatherapp.domain.GetCitiesFromApiUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test

import org.junit.Assert.*
import kotlin.coroutines.suspendCoroutine

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun getCities() {
        var list: List<City>
        GlobalScope.launch {
            list = WeatherApi.retrofitService.getCityList()
            print(list)
        }

    }
}