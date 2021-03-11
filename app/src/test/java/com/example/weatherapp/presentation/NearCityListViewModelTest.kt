package com.example.weatherapp.presentation

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetCitiesUseCase
import com.example.weatherapp.domain.GetLocationUseCase
import com.example.weatherapp.presentation.nearCityList.NearCityListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NearCityListViewModelTest {

    private val getCitiesUseCase: GetCitiesUseCase = mockk()
    private val getLocationUseCase: GetLocationUseCase = mockk()
    private val city: City = mockk()
    private val cities = listOf(city)
    private val location: MutableLiveData<Location?> = mockk()


    private val nearCityListViewModel = NearCityListViewModel(getCitiesUseCase, getLocationUseCase)


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    @Test
    fun `get cities EXPECT set of city in cities list`() = runBlocking {

        val locationHere = location

        coEvery { getLocationUseCase(locationHere) }
        coVerify { getLocationUseCase(location) }

        getLocationUseCase(locationHere)

        coEvery {
            getCitiesUseCase(
                location.value!!.latitude,
                location.value!!.longitude
            )
        } returns cities


        coVerify { getCitiesUseCase(location.value!!.latitude, location.value!!.longitude) }



        nearCityListViewModel.setLocation()
        nearCityListViewModel.getCities()


        assertEquals(cities, nearCityListViewModel.cityList.value)
    }

}