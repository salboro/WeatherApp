package com.example.weatherapp.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val API_KEY = "b80b396b64b870b8a0fd9275888daf7e"

const val SEARCH_IMG_URL = "https://www.googleapis.com/customsearch/v1"
private const val SEARCH_IMG_API_KEY = "AIzaSyA8doUUIsxBpASl9Mia-HXKxzgeKm5-EzM"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface WeatherApiService{
    @GET("find")
    suspend fun getCityList(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appId: String = API_KEY,
        @Query("cnt") count: Int = 45,
        @Query("units") units: String = "metric"
    ): Cities

    @GET("weather")
    suspend fun getCity(
        @Query("id") id: Long,
        @Query("appid") appId: String = API_KEY,
        @Query("lang") language: String = "EN",
        @Query("units") units: String = "metric"
    ): City

    @GET("weather")
    suspend fun getCityByName(
        @Query("q") name: String,
        @Query("appid") appId: String = API_KEY,
        @Query("units") units: String = "metric"
    ): City

    @GET()
    suspend fun getCityImage(
        @Url url: String,
        @Query("cx") searchSystemId: String = "1852c372bd4076462",
        @Query("key") appId: String = SEARCH_IMG_API_KEY,
        @Query("q") name: String,
        @Query("search_type") searchType: String = "image",
        @Query("large") large: String = "large",
        @Query("num") numOfItem: Int = 1
    ): Items
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}