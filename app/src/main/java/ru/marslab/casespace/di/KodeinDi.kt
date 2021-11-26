package ru.marslab.casespace.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import ru.marslab.casespace.data.StorageImpl
import ru.marslab.casespace.data.room.CaseSpaceDatabase
import ru.marslab.casespace.domain.repository.Storage

val storageModule = DI.Module("resourceModule") {
    bindProvider<SharedPreferences> {
        instance<Context>().getSharedPreferences("local_settings", Context.MODE_PRIVATE)
    }
    bindProvider {
        Room.databaseBuilder(instance(), CaseSpaceDatabase::class.java, "cs.db").build()
    }
    bindSingleton<Storage> {
        StorageImpl(instance(), instance())
    }
}