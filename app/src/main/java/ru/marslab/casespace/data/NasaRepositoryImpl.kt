package ru.marslab.casespace.data

import ru.marslab.casespace.data.retrofit.NasaApi
import ru.marslab.casespace.domain.repository.NasaRepository

private const val REPO_NAME = "Nasa Repository"

class NasaRepositoryImpl(nasaApi: NasaApi): NasaRepository {
    override fun getRepoName(): String = REPO_NAME

    override suspend fun getImageOfDay(date: String): String? {
        return null
    }
}