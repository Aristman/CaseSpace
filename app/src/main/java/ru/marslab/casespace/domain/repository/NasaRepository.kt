package ru.marslab.casespace.domain.repository

interface NasaRepository {
    fun getRepoName(): String
    suspend fun getImageOfDay(date: String): String?
}