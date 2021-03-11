package com.example.weatherapp.data

import android.app.Activity
import android.content.Context
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.database.WeatherAppDatabaseDao
import com.example.weatherapp.data.location.WeatherAppLocationService
import com.example.weatherapp.data.network.City
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class CityRepositoryImplTest {
    private val database: WeatherAppDatabaseDao = mockk()

    private val activity: Activity = mockk()
    private val context: Context = mockk()
    private val locationService: WeatherAppLocationService =
        WeatherAppLocationService(context, activity)

    private val repository = CityRepositoryImpl(database, locationService)

    private val city: City = mockk()
    private val location = MutableLiveData<Location?>()


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `get location EXPECT location change`() = runBlocking {
        coEvery { locationService.getLastLocation { location.value = it } } just runs

        repository.getLocation(location)

        coVerify { locationService.getLastLocation { location.value = it } }
    }


}