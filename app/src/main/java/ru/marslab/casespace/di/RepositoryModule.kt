package ru.marslab.casespace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.marslab.casespace.data.NasaRepositoryImpl
import ru.marslab.casespace.data.retrofit.NasaApi
import ru.marslab.casespace.domain.repository.NasaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNasaRepository(api: NasaApi): NasaRepository =
        NasaRepositoryImpl(api)
}