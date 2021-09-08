package ru.marslab.casespace.data

import ru.marslab.casespace.BuildConfig
import ru.marslab.casespace.domain.repository.Storage

class StorageImpl: Storage {
    override fun getNasaApikey(): String = BuildConfig.NASA_APIKEY
}