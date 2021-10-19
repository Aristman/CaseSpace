package ru.marslab.casespace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.marslab.casespace.domain.interactor.ApodInteractor
import ru.marslab.casespace.domain.interactor.ApodInteractorImpl
import ru.marslab.casespace.domain.interactor.NotesInteractor
import ru.marslab.casespace.domain.interactor.NotesInteractorImpl
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.domain.repository.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun provideNotesInteractor(storage: Storage): NotesInteractor =
        NotesInteractorImpl(storage)

    @Singleton
    @Provides
    fun provideApodInteractor(nasaRepository: NasaRepository): ApodInteractor =
        ApodInteractorImpl(nasaRepository)
}