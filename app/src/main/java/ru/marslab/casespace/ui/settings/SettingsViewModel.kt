package ru.marslab.casespace.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.marslab.casespace.domain.repository.Storage
import javax.inject.Inject

data class SettingsState(val theme: Int?)

sealed class SettingsAction {
    object Open : SettingsAction()
    object Close : SettingsAction()
    object ApplyTheme : SettingsAction()
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val storage: Storage
) : ViewModel() {
    private var _settingsState = MutableStateFlow(SettingsState(null))
    val settingsState = _settingsState.asStateFlow()

    private var _settingsAction = MutableSharedFlow<SettingsAction>()
    val settingsAction = _settingsAction.asSharedFlow()

    fun getCurrentTheme() {
        viewModelScope.launch {
            _settingsState.emit(SettingsState(storage.getTheme()))
        }
    }

    fun saveCurrentTheme(theme: Int) {
        viewModelScope.launch {
            storage.saveTheme(theme)
            _settingsAction.emit(SettingsAction.ApplyTheme)
            _settingsAction.tryEmit(SettingsAction.ApplyTheme)
        }
    }

    fun closeFragment() {
        viewModelScope.launch {
            _settingsAction.emit(SettingsAction.Close)
        }
    }
}