package ru.marslab.casespace.domain.repository

object Constant {
    private const val BASE_API_PATH = "https://api.nasa.gov/"
    private const val ERROR_LOAD_DATA = "Ошибка загрузки данных: %s"

    const val NO_INTERNET_CONNECTION = "Нет соединения с интернетом. Проверьте настройки."
    const val DATE_FORMAT_STRING = "yyyy-MM-dd"
    const val HTTP_ERROR_CODE = 400

    const val SETTING_THEME = "SETTING_THEME"

    fun getBaseApiPath(): String = BASE_API_PATH
    fun getLoadErrorString(message: String): String =
        String.format(ERROR_LOAD_DATA, message)
}