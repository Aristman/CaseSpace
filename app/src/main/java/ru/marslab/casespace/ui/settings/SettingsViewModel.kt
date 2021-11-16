package ru.marslab.casespace.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.domain.repository.Storage
import ru.marslab.casespace.ui.settings.view.AppTheme
import javax.inject.Inject

data class SettingsState(val theme: Int)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storage: Storage
) : ViewModel() {
    private var _settingsState = MutableStateFlow(SettingsState(AppTheme.DEFAULT_THEME.themeId))
    val settingsState = _settingsState.asStateFlow()

    fun getCurrentTheme() {
        viewModelScope.launch {
            _settingsState.emit(SettingsState(storage.getTheme()))
        }
    }

    fun saveCurrentTheme(theme: Int) {
        viewModelScope.launch {
            storage.saveTheme(theme)
            _settingsState.emit(SettingsState(theme))
        }
    }
}