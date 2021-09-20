package ru.marslab.casespace.data.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.marslab.casespace.data.model.ApodNW
import ru.marslab.casespace.data.model.EarthNW

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getImageOfDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String? = null
    ): Response<ApodNW>

    @GET("planetary/earth/assets")
    suspend fun getEarthAsset(
        @Query("api_key") apiKey: String,
        @Query("lon") lon: Float,
        @Query("lat") lat: Float,
        @Query("date") date: String,
        @Query("dim") dim: Float? = null
    ): Response<EarthNW>


}