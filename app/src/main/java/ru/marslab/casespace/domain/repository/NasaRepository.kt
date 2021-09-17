package ru.marslab.casespace.domain.repository

import ru.marslab.casespace.domain.model.PictureOfDay


interface NasaRepository {
    fun getRepoName(): String

    @Throws(Exception::class)
    suspend fun getPictureOfDay(date: String?): PictureOfDay?
}