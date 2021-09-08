package ru.marslab.casespace.domain.repository

object Constant {
    private const val BASE_API_PATH = "https://api.nasa.gov/"

    fun getBaseApiPath(): String = BASE_API_PATH
}