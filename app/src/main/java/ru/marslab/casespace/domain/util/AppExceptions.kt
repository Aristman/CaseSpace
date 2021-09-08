package ru.marslab.casespace.domain.util

import ru.marslab.casespace.domain.repository.Constant

class RepositoryLoadException(private val error: String): Exception() {
    override val message: String
        get() = String.format(Constant.ERROR_LOAD_DATA_FROM_REPOSITORY, error)
}

class DataLoadException(private val error: String): Exception() {
    override val message: String
        get() = String.format(Constant.ERROR_LOAD_DATA, error)
}