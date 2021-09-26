package ru.marslab.casespace.ui.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.marslab.casespace.domain.repository.Storage
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storage: Storage
) : ViewModel() {
    fun getCurrentTheme() = storage.getTheme()

    fun saveCurrentTheme(theme: Int) {
        storage.saveTheme(theme)
    }

}