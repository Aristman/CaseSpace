package ru.marslab.casespace.domain.interactor

import ru.marslab.casespace.domain.model.PictureOfDay

interface ApodInteractor {
    suspend fun getPictureOfDay(date: String?): PictureOfDay?
    fun getRepoName(): String
}