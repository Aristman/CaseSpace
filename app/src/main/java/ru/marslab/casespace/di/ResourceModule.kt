package ru.marslab.casespace.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.marslab.casespace.AppDispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ResourceModule {

    @Singleton
    @Provides
    fun provideDispatchers(): AppDispatchers = object : AppDispatchers {
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main

    }
}