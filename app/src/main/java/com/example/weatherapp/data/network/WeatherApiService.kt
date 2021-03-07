package com.example.weatherapp.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private const val API_KEY = "b80b396b64b870b8a0fd9275888daf7e"

const val IMG_URL = "https://openweathermap.org/img/wn/%s@2x.png"

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
        @Query("lat") latitude: Double = 56.488430,
        @Query("lon") longitude: Double = 84.948047,
        @Query("appid") appId: String = API_KEY,
        @Query("cnt") count: Int = 12
        ): Cities
}

object WeatherApi {
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}