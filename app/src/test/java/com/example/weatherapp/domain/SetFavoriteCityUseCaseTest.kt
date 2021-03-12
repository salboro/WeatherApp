package com.example.weatherapp.domain

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.database.FavoriteCity
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SetFavoriteCityUseCaseTest {
    private val cityRepository: CityRepository = mockk()
    private val city: FavoriteCity = mockk()
    private val location = MutableLiveData<Location?>()
    private val id: Long = 1

    private val setFavoriteCityUseCase = SetFavoriteCityUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `set favorite city EXPECT set favorite city`() = runBlocking {
        coEvery { cityRepository.setFavoriteCity(id) } just runs

        setFavoriteCityUseCase(id)

        coVerify { cityRepository.setFavoriteCity(id) }
    }
}