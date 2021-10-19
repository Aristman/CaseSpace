package ru.marslab.casespace.domain.interactor

import ru.marslab.casespace.domain.model.PictureOfDay
import ru.marslab.casespace.domain.repository.NasaRepository

class ApodInteractorImpl(private val nasaRepository: NasaRepository) : ApodInteractor {

    override suspend fun getPictureOfDay(date: String?): PictureOfDay? =
        nasaRepository.getPictureOfDay(date = date)

    override fun getRepoName(): String =
        nasaRepository.getRepoName()

}