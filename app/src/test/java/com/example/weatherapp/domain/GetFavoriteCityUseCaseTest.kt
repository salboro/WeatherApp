package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.database.FavoriteCities
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetFavoriteCityUseCaseTest {
    private val cityRepository: CityRepository = mockk()
    private val city: FavoriteCities = mockk()

    private val getFavoriteCityUseCase = GetFavoriteCityUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `get city by name EXPECT city`() = runBlocking {
        coEvery { cityRepository.getFavoriteCity(1) } returns city

        val city2 = getFavoriteCityUseCase(1)

        assertEquals(city, city2)
    }

}
