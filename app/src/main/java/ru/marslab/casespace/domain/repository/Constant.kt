package ru.marslab.casespace.domain.repository

object Constant {
    private const val BASE_API_PATH = "https://api.nasa.gov/"
    private const val ERROR_LOAD_DATA = "Ошибка загрузки данных: %s"

    const val NO_INTERNET_CONNECTION = "Нет соединения с интернетом. Проверьте настройки."
    const val DATE_FORMAT_STRING = "yyyy-MM-dd"
    const val HTTP_ERROR_CODE = 400

    const val SETTING_THEME = "SETTING_THEME"
    const val NASA_TIME_ZONE = "Etc/GMT+4"

    const val GPS_REFRESH_PERIOD = 6000L
    const val GPS_DISTANCE = 100f


    fun getBaseApiPath(): String = BASE_API_PATH
    fun getLoadErrorString(message: String): String =
        String.format(ERROR_LOAD_DATA, message)
}