package ru.marslab.casespace.ui.settings

import android.content.res.Resources
import androidx.annotation.StyleRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.domain.repository.Storage
import javax.inject.Inject

data class SettingsState(@StyleRes val themeId: Int)

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storage: Storage
) : ViewModel() {
    private var _settingsState = MutableStateFlow(SettingsState(AppTheme.DEFAULT_THEME.themeId))
    val settingsState = _settingsState.asStateFlow()

    private var _appTheme = MutableStateFlow<Resources.Theme?>(null)
    private val appTheme = _appTheme.asStateFlow()

    fun loadCurrentTheme() {
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