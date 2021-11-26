package ru.marslab.casespace

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXContextTranslators
import org.kodein.di.bindProvider
import ru.marslab.casespace.di.storageModule

@HiltAndroidApp
class App : Application(), DIAware {
    override val di = DI {
        import(androidXContextTranslators)
        import(storageModule)
        bindProvider<Context> { applicationContext }

        bindProvider<AppDispatchers> {
            object : AppDispatchers {
                override val io: CoroutineDispatcher
                    get() = Dispatchers.IO
                override val main: CoroutineDispatcher
                    get() = Dispatchers.Main
            }
        }
    }
}