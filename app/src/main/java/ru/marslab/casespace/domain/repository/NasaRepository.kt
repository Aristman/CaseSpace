package ru.marslab.casespace.domain.repository



interface NasaRepository {
    fun getRepoName(): String

    @Throws(Exception::class)
    suspend fun getImageOfDay(date: String): String?
}