package ru.marslab.casespace.domain.repository

object Constant {
    private const val BASE_NASA_API_PATH = "https://api.nasa.gov/"
    private const val BASE_EPIC_API_PATH = "https://epic.gsfc.nasa.gov/"
    private const val EPIC_API_PATH = "api/"
    private const val EPIC_ARCHIVE_PATH = "archive/"
    const val EPIC_COLLECTION_NATURAL = "natural"
    const val EPIC_COLLECTION_ENHANCED = "enhanced"
    const val PNG_IMAGE_TYPE = "png"
    const val JPG_IMAGE_TYPE = "jpg"
    const val THUMBS_IMAGE_TYPE = "thumbs"
    private const val ERROR_LOAD_DATA = "Ошибка загрузки данных: %s"

    const val NO_INTERNET_CONNECTION = "Нет соединения с интернетом. Проверьте настройки."
    const val DATE_FORMAT_STRING = "yyyy-MM-dd"
    const val HTTP_ERROR_CODE = 400

    const val SETTING_THEME = "SETTING_THEME"
    const val NASA_TIME_ZONE = "Etc/GMT+4"

    const val GPS_REFRESH_PERIOD = 6000L
    const val GPS_DISTANCE = 100f


    fun getNasaBaseApiPath(): String = BASE_NASA_API_PATH
    fun getEpicBaseApiPath(): String = BASE_EPIC_API_PATH + EPIC_API_PATH
    fun getEpicImageArchivePath(): String = BASE_EPIC_API_PATH + EPIC_ARCHIVE_PATH
    fun getLoadErrorString(message: String): String =
        String.format(ERROR_LOAD_DATA, message)
}