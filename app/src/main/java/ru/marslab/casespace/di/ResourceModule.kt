package ru.marslab.casespace.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.marslab.casespace.AppDispatchers
import ru.marslab.casespace.data.room.CaseSpaceDatabase
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

    @Singleton
    @Provides
    fun provideSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences("local_settings", Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideDatabase(appContext: Context): CaseSpaceDatabase =
        Room.databaseBuilder(appContext, CaseSpaceDatabase::class.java, "cs.db")
            .build()

    @Singleton
    @Provides
    fun provideAppContext(app: Application): Context =
        app.applicationContext
}