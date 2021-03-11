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

class GetCitiesUseCaseTest {
    private val cityRepository: CityRepository = mockk()
    private val city: City = mockk()

    private val getCitiesUseCase = GetCitiesUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `get cities EXPECT list of city`() = runBlocking {
        coEvery { cityRepository.getCities(52.1, 24.1) } returns listOf(city)

        val cities = getCitiesUseCase(52.1, 24.1)

        assertEquals(listOf(city), cities)
    }
}