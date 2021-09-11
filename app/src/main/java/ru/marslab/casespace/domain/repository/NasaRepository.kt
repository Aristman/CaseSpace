package ru.marslab.casespace.domain.repository

import ru.marslab.casespace.domain.model.Picture


interface NasaRepository {
    fun getRepoName(): String

    @Throws(Exception::class)
    suspend fun getPictureOfDay(date: String?): Picture?
}