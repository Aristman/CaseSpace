package ru.marslab.casespace.ui

import androidx.annotation.StyleRes
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.marslab.casespace.domain.repository.Storage
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storage: Storage
) : ViewModel() {

    fun getCurrentTheme(): Int =
        storage.getTheme()

    fun saveCurrentTheme(@StyleRes theme: Int) {
        storage.saveTheme(theme)
    }
}