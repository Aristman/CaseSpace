package ru.marslab.casespace.data

import android.accounts.NetworkErrorException
import com.google.gson.Gson
import ru.marslab.casespace.data.mapper.toDomain
import ru.marslab.casespace.data.model.ErrorNW
import ru.marslab.casespace.data.retrofit.NasaApi
import ru.marslab.casespace.domain.model.Picture
import ru.marslab.casespace.domain.repository.Constant
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.domain.repository.Storage


class NasaRepositoryImpl(
    private val nasaApi: NasaApi,
    private val storage: Storage
) : NasaRepository {
    companion object {
        private const val REPO_NAME = "NASA API Repository"
    }

    override fun getRepoName(): String = REPO_NAME

    @Throws(Exception::class)
    override suspend fun getPictureOfDay(date: String?): Picture? {
        val pictureOfDay = nasaApi.getImageOfDay(storage.getNasaApikey(), date)
        when {
            pictureOfDay.isSuccessful -> {
                return pictureOfDay.body()?.toDomain()
            }
            pictureOfDay.raw().code == Constant.HTTP_ERROR_CODE -> {
                val response =
                    Gson().fromJson(pictureOfDay.errorBody()?.string(), ErrorNW::class.java)
                throw NetworkErrorException(Constant.getLoadErrorString(response.msg))
            }
            else -> throw NetworkErrorException(Constant.getLoadErrorString(REPO_NAME))
        }
    }
}