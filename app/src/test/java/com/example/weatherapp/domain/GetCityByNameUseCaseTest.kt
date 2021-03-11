package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.network.City
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetCityByNameUseCaseTest {

    private val cityRepository: CityRepository = mockk()
    private val city: City = mockk()

    private val getCityByNameUseCase = GetCityByNameUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `get city by name EXPECT city`() = runBlocking {
        coEvery { cityRepository.getCityByName("moscow") } returns city

        val city2 = getCityByNameUseCase("moscow")

        assertEquals(city, city2)
    }

}