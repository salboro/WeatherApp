package com.example.weatherapp.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.CoroutineTestRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DeleteFavoriteCityUseCaseTest {
    private val cityRepository: CityRepository = mockk()

    private val deleteFavoriteCityUseCase = DeleteFavoriteCityUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `delete favorite city EXPECT delete city from favorite list`() = runBlocking {
        coEvery { cityRepository.deleteFavoriteCity(1) } just runs

        deleteFavoriteCityUseCase(1)

        coVerify { cityRepository.deleteFavoriteCity(1) }
    }

}
