package ru.marslab.casespace.domain.repository

object Constant {
    private const val BASE_API_PATH = "https://api.nasa.gov/"

    const val DATE_FORMAT_STRING = "yyyy-MM-dd"
    const val ERROR_LOAD_DATA = "Ошибка загрузки данных: %s"
    const val HTTP_ERROR_CODE = 400

    fun getBaseApiPath(): String = BASE_API_PATH
    fun getLoadErrorString(message: String): String =
        String.format(ERROR_LOAD_DATA, message)
}