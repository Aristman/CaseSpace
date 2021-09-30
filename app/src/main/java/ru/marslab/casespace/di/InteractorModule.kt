package ru.marslab.casespace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.domain.interactor.NotesInteractor
import ru.marslab.casespace.domain.interactor.impl.NotesInteractorImpl
import ru.marslab.casespace.domain.repository.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Singleton
    @Provides
    fun provideNotesInteractor(storage: Storage, dispatchers: AppDispatchers): NotesInteractor =
        NotesInteractorImpl(storage, dispatchers)
}