package com.example.weatherapp.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.database.FavoriteCities
import com.example.weatherapp.data.network.City
import com.example.weatherapp.domain.DeleteFavoriteCityUseCase
import com.example.weatherapp.domain.GetFavoriteCityUseCase
import com.example.weatherapp.domain.SetFavoriteCityUseCase
import com.example.weatherapp.presentation.detail.DetailViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailViewModelTest {

    private val viewCity: City = mockk()
    private val favoriteCity: FavoriteCities = FavoriteCities(1)
    private val id: Long = 1
    private val getFavoriteCityUseCase: GetFavoriteCityUseCase = mockk()
    private val setFavoriteCityUseCase: SetFavoriteCityUseCase = mockk()
    private val deleteFavoriteCityUseCase: DeleteFavoriteCityUseCase = mockk()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()


    @Test
    fun `set city condition EXPECT set condition in condition flag`() = runBlocking {
        every { viewCity.id } returns 1
        coEvery { setFavoriteCityUseCase(viewCity.id) } just runs
        coEvery { deleteFavoriteCityUseCase(favoriteCity.id) } just runs
        coEvery { getFavoriteCityUseCase(viewCity.id) } returns favoriteCity

        val detailViewModel = DetailViewModel(
            viewCity,
            getFavoriteCityUseCase,
            setFavoriteCityUseCase,
            deleteFavoriteCityUseCase
        )

        coVerify { setFavoriteCityUseCase(id) }
        coVerify { deleteFavoriteCityUseCase(id) }
        coVerify { getFavoriteCityUseCase(id) }
    }
}