package ru.marslab.casespace.data

import android.accounts.NetworkErrorException
import com.google.gson.Gson
import ru.marslab.casespace.data.model.ErrorNW
import ru.marslab.casespace.data.retrofit.NasaApi
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.domain.repository.Storage
import ru.marslab.casespace.domain.util.DataLoadException
import ru.marslab.casespace.domain.util.RepositoryLoadException

private const val REPO_NAME = "NASA API Repository"

class NasaRepositoryImpl(
    private val nasaApi: NasaApi,
    private val storage: Storage
) : NasaRepository {
    override fun getRepoName(): String = REPO_NAME

    @Throws(Exception::class)
    override suspend fun getImageOfDay(date: String): String? {
        val imageOfDay = nasaApi.getImageOfDay(storage.getNasaApikey(), date)
        if (imageOfDay.isSuccessful && imageOfDay.body() != null) {
            return imageOfDay.body()?.url
        } else if (imageOfDay.raw().code == 400) {
            val response = Gson().fromJson(imageOfDay.errorBody()?.string(), ErrorNW::class.java)
            throw DataLoadException(response.msg)
        } else throw NetworkErrorException()
    }
}