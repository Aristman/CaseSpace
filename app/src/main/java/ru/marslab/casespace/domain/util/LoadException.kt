package ru.marslab.casespace.domain.util

import ru.marslab.casespace.domain.repository.Constant

class LoadException(private val repoName: String): Throwable() {
    override val message: String
        get() = String.format(Constant.ERROR_LOAD_DATA_FROM_REPOSITORY, repoName)
}