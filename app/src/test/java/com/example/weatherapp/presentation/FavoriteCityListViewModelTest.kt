package com.example.weatherapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.database.FavoriteCity
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.GetFavoriteCitiesFromDBUseCase
import com.example.weatherapp.domain.GetFavoriteCitiesUseCase
import com.example.weatherapp.presentation.favoriteCityList.FavoriteCityListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class FavoriteCityListViewModelTest {

    private val getFavoriteCitiesFromDBUseCase: GetFavoriteCitiesFromDBUseCase = mockk()
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase = mockk()

    private val city: City = mockk()
    private val cities = listOf(city)
    private val favoriteCity: FavoriteCity = mockk()
    private val favoriteCitiesList = listOf(favoriteCity)


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    @Test
    fun `get cities EXPECT set list of favorite city in cities list()`() = runBlocking {
        coEvery { getFavoriteCitiesFromDBUseCase() } returns favoriteCitiesList
        coEvery { getFavoriteCitiesUseCase(favoriteCitiesList) } returns cities

        val favoriteCityListViewModel =
            FavoriteCityListViewModel(getFavoriteCitiesUseCase, getFavoriteCitiesFromDBUseCase)

        coVerify { getFavoriteCitiesFromDBUseCase() }
        coVerify { getFavoriteCitiesUseCase(favoriteCitiesList) }

        favoriteCityListViewModel.refreshCities()

        assertEquals(cities, favoriteCityListViewModel.cityList.value)

    }
}