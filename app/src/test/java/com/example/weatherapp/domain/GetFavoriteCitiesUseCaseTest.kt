package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.database.FavoriteCity
import com.example.weatherapp.data.network.City
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetFavoriteCitiesUseCaseTest {
    private val cityRepository: CityRepository = mockk()
    private val city: City = mockk()
    private val favoriteCity: FavoriteCity = mockk()

    private val getFavoriteCitiesUseCase = GetFavoriteCitiesUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `get city by name EXPECT city`() = runBlocking {
        coEvery { cityRepository.getFavoriteCities(listOf(favoriteCity)) } returns listOf(city)

        val cities = getFavoriteCitiesUseCase(listOf(favoriteCity))

        assertEquals(listOf(city), cities)
    }
}