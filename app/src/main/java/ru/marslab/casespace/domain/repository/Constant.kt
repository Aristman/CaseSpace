package ru.marslab.casespace.domain.repository

object Constant {
    const val DATE_FORMAT_STRING = "yyyy-MM-dd"
    const val ERROR_LOAD_DATA_FROM_REPOSITORY = "Ошибка загрузки данных из репозитория %s"
    const val ERROR_LOAD_DATA = "Ошибка загрузки данных: %s"
    const val HTTP_ERROR_CODE = 400

    private const val BASE_API_PATH = "https://api.nasa.gov/"
    fun getBaseApiPath(): String = BASE_API_PATH
}