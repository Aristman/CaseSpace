package ru.marslab.casespace.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.marslab.casespace.data.NasaRepositoryImpl
import ru.marslab.casespace.data.StorageImpl
import ru.marslab.casespace.data.retrofit.NasaApi
import ru.marslab.casespace.data.room.CaseSpaceDatabase
import ru.marslab.casespace.domain.repository.NasaRepository
import ru.marslab.casespace.domain.repository.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNasaRepository(api: NasaApi, storage: Storage): NasaRepository =
        NasaRepositoryImpl(api, storage)

    @Singleton
    @Provides
    fun provideStorage(sharedPreferences: SharedPreferences, database: CaseSpaceDatabase): Storage =
        StorageImpl(sharedPreferences, database)

}