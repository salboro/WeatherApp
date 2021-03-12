package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.database.FavoriteCity
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetFavoriteCitiesFromDBUseCaseTest {
    private val cityRepository: CityRepository = mockk()
    private val city: FavoriteCity = mockk()

    private val getFavoriteCitiesFromDBUseCase = GetFavoriteCitiesFromDBUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `get favorite cities EXPECT list of favorite cities`() = runBlocking {
        coEvery { cityRepository.getFavoriteCitiesFromDB() } returns listOf(city)

        val cities = getFavoriteCitiesFromDBUseCase()

        assertEquals(listOf(city), cities)
    }
}