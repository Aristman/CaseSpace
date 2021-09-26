package ru.marslab.casespace.domain.repository

import ru.marslab.casespace.domain.model.EarthImage
import ru.marslab.casespace.domain.model.MarsImage
import ru.marslab.casespace.domain.model.MarsRover
import ru.marslab.casespace.domain.model.PictureOfDay


interface NasaRepository {
    fun getRepoName(): String

    @Throws(Exception::class)
    suspend fun getPictureOfDay(date: String?): PictureOfDay?

    @Throws(Exception::class)
    suspend fun getEarthAssets(lon: Float, lat: Float, date: String, dim: Float? = 0.1f): String?

    @Throws(Exception::class)
    suspend fun getEpicImageList(collectionType: String, imageType: String): List<EarthImage>?

    @Throws(Exception::class)
    suspend fun getMarsPhotos(date: String): List<MarsImage>?

    @Throws(Exception::class)
    suspend fun getMarsRoverInfo(): MarsRover?
}