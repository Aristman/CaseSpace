package ru.marslab.casespace.data.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.marslab.casespace.data.model.ApodNW

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getImageOfDay(
        @Query("date") date: String
    ): Response<ApodNW>
}