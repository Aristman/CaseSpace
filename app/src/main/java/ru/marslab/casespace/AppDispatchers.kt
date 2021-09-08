package ru.marslab.casespace

import kotlinx.coroutines.CoroutineDispatcher

interface AppDispatchers {
    val io: CoroutineDispatcher
    val main: CoroutineDispatcher
}