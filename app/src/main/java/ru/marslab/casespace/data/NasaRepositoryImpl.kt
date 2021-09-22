package ru.marslab.casespace.data

import android.accounts.NetworkErrorException
import com.google.gson.Gson
import retrofit2.Response
import ru.marslab.casespace.data.mapper.toDomain
import ru.marslab.casespace.data.model.ErrorNW
import ru.marslab.casespace.data.retrofit.NasaApi
import ru.marslab.casespace.domain.model.EarthImage
import ru.marslab.casespace.domain.model.PictureOfDay
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
    override suspend fun getPictureOfDay(date: String?): PictureOfDay? {
        val pictureOfDay = nasaApi.getImageOfDay(storage.getNasaApikey(), date)
        return checkResponse(pictureOfDay, REPO_NAME).body()?.toDomain()
    }

    @Throws(Exception::class)
    override suspend fun getEarthAssets(
        lon: Float,
        lat: Float,
        date: String,
        dim: Float?
    ): String? {
        val earthAsset = nasaApi.getEarthAsset(storage.getNasaApikey(), lon, lat, date, dim)
        return checkResponse(earthAsset, REPO_NAME).body()?.url
    }

    override suspend fun getEpicImageList(collectionType: String): List<EarthImage>? {
        val epicImages = nasaApi.getEpicImages(
            url = Constant.getEpicBaseApiPath() + collectionType,
        )
        return checkResponse(epicImages, REPO_NAME).body()
            ?.map { it.toDomain(collectionType, Constant.DEFAULT_IMAGE_TYPE) }
    }
}

@Throws(Exception::class)
internal fun <T> checkResponse(response: Response<T>, repoName: String): Response<T> {
    when {
        response.isSuccessful -> {
            return response
        }
        response.raw().code == Constant.HTTP_ERROR_CODE -> {
            val responseFromJson =
                Gson().fromJson(response.errorBody()?.string(), ErrorNW::class.java)
            throw NetworkErrorException(Constant.getLoadErrorString(responseFromJson.msg))
        }
        else -> throw NetworkErrorException(Constant.getLoadErrorString(repoName))
    }
}