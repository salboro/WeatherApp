package com.example.weatherapp.domain

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.CoroutineTestRule
import com.example.weatherapp.data.network.City
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class GetLocationUseCaseTest {
    private val cityRepository: CityRepository = mockk()
    private val city: City = mockk()
    private val location = MutableLiveData<Location?>()

    private val getLocationUseCase = GetLocationUseCase(cityRepository)

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `get location EXPECT set location`() = runBlocking {
        coEvery { cityRepository.getLocation(location) } just runs

        getLocationUseCase(location)

        coVerify { cityRepository.getLocation(location) }
    }

}
